package com.mundaco.recipepuppy

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.view.View
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeRequest
import com.mundaco.recipepuppy.rules.RxImmediateSchedulerRule
import com.mundaco.recipepuppy.ui.recipe.RecipeListViewModel
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit

class RecipeViewModelTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    var instantExecutorRule: TestRule = InstantTaskExecutorRule()

    // Helpers
    @Mock
    lateinit var recipeRepository: RecipeRepository

    private val recipeList = arrayOf(
        Recipe("This is a test title","test","test","test"),
        Recipe("This is another test title","test","test","test"),
        Recipe("This is yet another test title","test","test","test"),
        Recipe("And another test title","test","test","test"),
        Recipe("Fifth test title","test","test","test"),
        Recipe("Sixth test title","test","test","test")
    )

    private lateinit var sut: RecipeListViewModel

    // Tests
    @Before
    fun setUp() {

        sut = RecipeListViewModel(recipeRepository)


        `when`(recipeRepository.searchRecipes(sut.recipeRequest)).thenAnswer { invocation ->
            val req = invocation.getArgument<RecipeRequest>(0)
            req.results = recipeList.filter { recipe ->
                recipe.title.contains(req.query)
            }.subList((req.page - 1) * 2, req.page * 2)
            Observable.just(req)
        }
    }

    @Test
    fun loadNewQueryResults_withEmptyQuery_YieldsEmptyRecipeList() {

        sut.loadNewQueryResults("")

        assertThat(sut.recipeList.value!!.size, `is`(0))

    }

    @Test
    fun loadNewQueryResults_withNonEmptyQuery_YieldsNonEmptyRecipeList() {

        sut.loadNewQueryResults("test")

        assertThat(sut.recipeList.value!!.size, `is`(2))

    }

    @Test
    fun loadNewQueryResults_withEmptyQuery_hidesLoadingIndicator() {

        sut.loadNewQueryResults("")

        assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }

    @Test
    fun loadNewQueryResults_withNonEmptyQuery_setsErrorMessageValueToNull() {

        sut.loadNewQueryResults("test")

        assertThat(sut.errorMessage.value, nullValue())
    }

    @Test
    fun loadNewQueryResults_withEmptyQuery_setsErrorMessageValueToNull() {

        sut.loadNewQueryResults("")

        assertThat(sut.errorMessage.value, nullValue())
    }

    @Test
    fun loadNewQueryResults_withNonEmptyQuery_hidesLoadingIndicator() {

        sut.loadNewQueryResults("test")

        assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }

    @Test
    fun loadNextPageResults_withEmptyQueryAndPageOne_ReturnsAnEmptyRecipeList() {

        sut.loadNewQueryResults("")
        sut.loadNextPageResults(1)

        assertThat(sut.recipeList.value!!.size, `is`(0))
    }

    @Test
    fun loadNextPageResults_withEmptyQueryAndPageTwo_ReturnsAnEmptyRecipeList() {

        sut.loadNewQueryResults("")
        sut.loadNextPageResults(2)

        assertThat(sut.recipeList.value!!.size, `is`(0))
    }

    @Test
    fun loadNextPageResults_withNonEmptyQueryAndPageOne_ReturnsTwoRecipes() {

        sut.loadNewQueryResults("test")
        sut.loadNextPageResults(1)

        assertThat(sut.recipeList.value!!.size, `is`(2))
    }

    @Test
    fun loadNextPageResults_withNonEmptyQueryAndPageTwo_ReturnsFourRecipes() {

        sut.loadNewQueryResults("test")
        sut.loadNextPageResults(2)

        assertThat(sut.recipeList.value!!.size, `is`(4))
    }

    @Test
    fun loadNextPageResults_withNonEmptyQueryAndPagePreviousToPageTwo_ReturnsFourRecipes() {

        sut.loadNewQueryResults("test")
        sut.loadNextPageResults(2)
        sut.loadNextPageResults(1)

        assertThat(sut.recipeList.value!!.size, `is`(4))
    }
}
