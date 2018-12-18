package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.domain.rules.RxImmediateSchedulerRule
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

class UseCaseSearchUnitTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    // Helpers
    @Mock
    lateinit var recipeRepository: RecipeRepository

    private lateinit var sut: RecipeSearchUseCase

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

        sut = RecipeSearchInteractor(recipeRepository)
    }

    @Test
    fun search_whenEmptyQuery_ReturnsEmptyList() {

        val result = sut.searchRecipes("")

        val testObserver = TestObserver<List<Recipe>>()
        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.assertValue(emptyList())
    }

    @Test
    fun search_whenNonEmptyQuery_ReturnsNonEmptyList() {

        val result = sut.searchRecipes("a")

        val testObserver = TestObserver<List<Recipe>>()
        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val listResult = testObserver.values()[0]
        assertThat(listResult.size, `is`(1))
        assertThat(listResult[0].title, `is`("a"))
    }
}