package com.mundaco.recipepuppy

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.rules.RxImmediateSchedulerRule
import com.mundaco.recipepuppy.ui.recipe.RecipeListViewModel
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertNull
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
    val rule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    var rule1: TestRule = InstantTaskExecutorRule()

    // Helpers
    @Mock
    lateinit var recipeRepository: RecipeRepository

    internal lateinit var sut: RecipeListViewModel

    // Tests
    @Before
    fun setUp() {

        val recipeList = arrayListOf<Recipe>()
        recipeList.add(Recipe("a","","",""))
        Mockito.`when`(recipeRepository.searchRecipes("")).thenReturn(
            Observable.just(emptyList())
        )
        Mockito.`when`(recipeRepository.searchRecipes("a")).thenReturn(
            Observable.just(recipeList)
        )

        sut = RecipeListViewModel(recipeRepository)
    }

    @Test
    fun text_onQueryTextChanged_withEmptyString_YieldsEmptyListAdapter() {

        sut.onQueryTextListener.onQueryTextChange("")


        assertThat(sut.recipeListAdapter.itemCount, `is`(0))

    }

    @Test
    fun text_onQueryTextChanged_withNonEmptyString_YieldsNonEmptyListAdapter() {

        sut.onQueryTextListener.onQueryTextChange("a")

        assertThat(sut.recipeListAdapter.itemCount, `is`(1))

    }

    @Test
    fun text_onQueryTextChanged_withValidString_setsErrorMessageValueToNull() {

        sut.onQueryTextListener.onQueryTextChange("a")

        assertNull(sut.errorMessage.value)

    }
}
