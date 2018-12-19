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

    @Mock
    private lateinit var sut: RecipeListViewModel

    // Tests
    @Before
    fun setUp() {

        val recipeList = arrayListOf<Recipe>()
        recipeList.add(Recipe("This is a test title","test","test","test"))
        `when`(recipeRepository.searchRecipes(RecipeRequest("test"))).thenAnswer { invocation ->
            Observable.just(recipeList.filter { recipe ->
                val query = invocation.getArgument<RecipeRequest>(0).query
                recipe.title.contains(query)
            })
        }

        sut = RecipeListViewModel(recipeRepository)
    }

    @Test
    fun onQueryTextChanged_withEmptyQuery_YieldsEmptyRecipeList() {

        sut.onQueryTextListener.onQueryTextChange("")

        assertThat(sut.recipeList.value!!.size, `is`(0))

    }

    @Test
    fun onQueryTextChanged_withNonEmptyQuery_YieldsNonEmptyRecipeList() {

        sut.onQueryTextListener.onQueryTextChange("test")

        assertThat(sut.recipeList.value!!.size, `is`(1))

    }

    @Test
    fun onQueryTextChanged_withEmptyQuery_hidesLoadingIndicator() {

        sut.onQueryTextListener.onQueryTextChange("")

        assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }

    @Test
    fun onQueryTextChanged_withNonEmptyQuery_setsErrorMessageValueToNull() {

        sut.onQueryTextListener.onQueryTextChange("test")

        assertThat(sut.errorMessage.value, nullValue())
    }

    @Test
    fun onQueryTextChanged_withEmptyQuery_setsErrorMessageValueToNull() {

        sut.onQueryTextListener.onQueryTextChange("")

        assertThat(sut.errorMessage.value, nullValue())
    }

    @Test
    fun onQueryTextChanged_withNonEmptyQuery_hidesLoadingIndicator() {

        sut.onQueryTextListener.onQueryTextChange("test")

        assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }

    @Test
    fun onListScrollChanged_() {

        //sut.onQueryTextListener.onQueryTextChange("test")

        //assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }

}
