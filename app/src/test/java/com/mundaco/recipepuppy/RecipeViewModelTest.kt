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


    private lateinit var sut: RecipeListViewModel

    // Tests
    @Before
    fun setUp() {

        sut = RecipeListViewModel(recipeRepository)

        val recipeList = arrayListOf<Recipe>()
        recipeList.add(Recipe("This is a test title","test","test","test"))

        `when`(recipeRepository.searchRecipes(sut.recipeRequest)).thenAnswer { invocation ->
            val req = invocation.getArgument<RecipeRequest>(0)
            req.results = recipeList.filter { recipe ->
                recipe.title.contains(req.query)
            }
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

        assertThat(sut.recipeList.value!!.size, `is`(1))

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



}
