package com.mundaco.recipepuppy

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeRequest
import com.mundaco.recipepuppy.rules.RxImmediateSchedulerRule
import com.mundaco.recipepuppy.ui.recipe.EndlessRecyclerViewScrollListener
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


    class LayoutManager: RecyclerView.LayoutManager() {

        override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {

            return RecyclerView.LayoutParams(100,100)
        }

    }

    @Mock
    val scrollListener = EndlessRecyclerViewScrollListener(LayoutManager())

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
        `when`(scrollListener.resetState()).then {  }

        sut = RecipeListViewModel(recipeRepository)
        sut.scrollListener = scrollListener
    }

    @Test
    fun sendNewRequest_withEmptyQuery_YieldsEmptyRecipeList() {

        sut.sendNewRequest("")

        assertThat(sut.recipeList.value!!.size, `is`(0))

    }

    @Test
    fun sendNewRequest_withNonEmptyQuery_YieldsNonEmptyRecipeList() {

        sut.sendNewRequest("test")

        assertThat(sut.recipeList.value!!.size, `is`(1))

    }

    @Test
    fun sendNewRequest_withEmptyQuery_hidesLoadingIndicator() {

        sut.sendNewRequest("")

        assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }

    @Test
    fun sendNewRequest_withNonEmptyQuery_setsErrorMessageValueToNull() {

        sut.sendNewRequest("test")

        assertThat(sut.errorMessage.value, nullValue())
    }

    @Test
    fun sendNewRequest_withEmptyQuery_setsErrorMessageValueToNull() {

        sut.sendNewRequest("")

        assertThat(sut.errorMessage.value, nullValue())
    }

    @Test
    fun sendNewRequest_withNonEmptyQuery_hidesLoadingIndicator() {

        sut.sendNewRequest("test")

        assertThat(sut.loadingVisibility.value, `is`(View.GONE))
    }



}
