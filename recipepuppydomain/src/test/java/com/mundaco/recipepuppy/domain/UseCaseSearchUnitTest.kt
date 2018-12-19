package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeRequest
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
        recipeList.add(Recipe("test","test","test","test"))
        Mockito.`when`(recipeRepository.searchRecipes(RecipeRequest("test"))).thenAnswer { invocation ->
            val recipeRequest = invocation.getArgument<RecipeRequest>(0)
            recipeRequest.results = recipeList.filter { recipe ->
                recipe.title.contains(recipeRequest.query) }
            Observable.just(recipeRequest)
        }

        sut = RecipeSearchInteractor(recipeRepository)
    }

    @Test
    fun search_whenEmptyQuery_ReturnsEmptyList() {

        val result = sut.searchRecipes(RecipeRequest(""))

        val testObserver = TestObserver<RecipeRequest>()
        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        assertThat(testObserver.values()[0].results, `is`(emptyList()))
    }

    @Test
    fun search_whenNonEmptyQuery_ReturnsNonEmptyList() {

        val result = sut.searchRecipes(RecipeRequest("test"))

        val testObserver = TestObserver<RecipeRequest>()
        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)

        val recipeRequest = testObserver.values()[0]
        assertThat(recipeRequest.results!!.size, `is`(1))
        assertThat(recipeRequest.results!![0].title, `is`("test"))
    }
}