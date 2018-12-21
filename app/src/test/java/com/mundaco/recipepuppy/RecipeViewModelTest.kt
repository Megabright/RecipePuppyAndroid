package com.mundaco.recipepuppy

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.view.View
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.domain.recipelist.RecipeListInteractor
import com.mundaco.recipepuppy.rules.RxImmediateSchedulerRule
import com.mundaco.recipepuppy.ui.recipelist.RecipeListViewModel
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
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
    var interactor = RecipeListInteractor(recipeRepository)

    private lateinit var sut: RecipeListViewModel

    // Tests
    @Before
    fun setUp() {

        sut = RecipeListViewModel(interactor)

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

    // TODO: Test paging
}
