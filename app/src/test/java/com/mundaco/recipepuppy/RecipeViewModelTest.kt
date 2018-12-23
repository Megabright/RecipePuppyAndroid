package com.mundaco.recipepuppy

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.view.View
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeRequest
import com.mundaco.recipepuppy.domain.recipelist.RecipeListInteractor
import com.mundaco.recipepuppy.rules.RxImmediateSchedulerRule
import com.mundaco.recipepuppy.ui.recipelist.RecipeListViewModel
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
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

    @Mock
    lateinit var interactor: RecipeListInteractor

    private lateinit var sut: RecipeListViewModel

    // Tests
    @Before
    fun setUp() {

        interactor = RecipeListInteractor(recipeRepository)

        sut = RecipeListViewModel(interactor)

        Mockito.`when`(recipeRepository.searchRecipes(sut.interactor.recipeRequest)).thenAnswer { invocation ->
            val req = invocation.getArgument<RecipeRequest>(0)
            req.results = recipeList.filter { recipe ->
                recipe.title.contains(req.query)
            }.subList((req.page - 1) * 2, req.page * 2)
            Observable.just(req)
        }

        interactor.requestState.observeForever {
            if(it != null) sut.onRequestStateChanged(it)
        }

    }

    @Test
    fun onQueryTextChanged_withEmptyQuery_hidesLoadingIndicator() {

        sut.onQueryTextListener.onQueryTextChange("")

        assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }

    @Test
    fun onQueryTextChanged_withNonEmptyQuery_hidesLoadingIndicator() {

        sut.onQueryTextListener.onQueryTextChange("test")

        assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }

    @Test
    fun onQueryTextChange_withNonEmptyQuery_setsErrorMessageValueToNull() {

        sut.onQueryTextListener.onQueryTextChange("test")

        assertThat(sut.errorMessage.value, nullValue())
    }

    @Test
    fun onQueryTextChange_withEmptyQuery_setsErrorMessageValueToNull() {

        sut.onQueryTextListener.onQueryTextChange("")

        assertThat(sut.errorMessage.value, nullValue())
    }

    @Test
    fun onEndOfPageReached_withEmptyQuery_hidesLoadingIndicator() {

        sut.onQueryTextListener.onQueryTextChange("")
        sut.onEndOfPageReached(1)

        assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }

    @Test
    fun onEndOfPageReached_withNonEmptyQuery_hidesLoadingIndicator() {

        sut.onQueryTextListener.onQueryTextChange("test")
        sut.onEndOfPageReached(1)

        assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }

    @Test
    fun onEndOfPageReached_withEmptyQuery_setsErrorMessageValueToNull() {

        sut.onQueryTextListener.onQueryTextChange("")
        sut.onEndOfPageReached(1)

        assertThat(sut.errorMessage.value, nullValue())
    }

    @Test
    fun onEndOfPageReached_withNonEmptyQuery_setsErrorMessageValueToNull() {

        sut.onQueryTextListener.onQueryTextChange("test")
        sut.onEndOfPageReached(1)

        assertThat(sut.errorMessage.value, nullValue())
    }

    @Test
    fun showRecipe_withEmptyQuery_setsSelectedRecipe() {


        sut.showRecipe(recipeList[0])

        assertThat(sut.interactor.selectedRecipe.value, `is`(recipeList[0]))
    }
}
