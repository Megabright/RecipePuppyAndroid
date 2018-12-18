package com.mundaco.recipepuppy

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
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

    private lateinit var sut: RecipeListViewModel

    // Tests
    @Before
    fun setUp() {

        val recipeList = arrayListOf<Recipe>()
        recipeList.add(Recipe("test","test","test","test"))
        Mockito.`when`(recipeRepository.searchRecipes(Mockito.anyString())).thenReturn(
            Observable.just(recipeList)
        )

        sut = RecipeListViewModel(recipeRepository)
    }

    @Test
    fun searchRecipes_withEmptyString_YieldsEmptyListAdapter() {

        sut.searchRecipes("")


        assertThat(sut.recipeList.value!!.size, `is`(0))

    }

    @Test
    fun searchRecipes_withNonEmptyString_YieldsNonEmptyListAdapter() {

        sut.searchRecipes("a")

        assertThat(sut.recipeList.value!!.size, `is`(1))

    }

    @Test
    fun searchRecipes_withValidString_setsErrorMessageValueToNull() {

        sut.searchRecipes("¡")

        assertThat(sut.errorMessage.value, nullValue())
    }
}
