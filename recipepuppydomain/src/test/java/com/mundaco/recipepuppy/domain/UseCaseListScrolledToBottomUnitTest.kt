package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeRequest
import com.mundaco.recipepuppy.domain.rules.RxImmediateSchedulerRule
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit

class UseCaseListScrolledToBottomUnitTest {

    @Rule
    @JvmField
    val mockitoRule = MockitoJUnit.rule()!!

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    // Helpers
    @Mock
    lateinit var recipeRepository: RecipeRepository

    private lateinit var sut: ListScrolledToBottomUseCase

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

        sut = ListScrolledToBottomInteractor(recipeRepository)
    }

    @Test
    fun requestRecipePage_returnsObservable() {

        val result = sut.requestRecipePage(RecipeRequest("test", 1))

        assertThat(result,instanceOf(Observable::class.java))

    }

    @Test
    fun requestRecipePage_withPageLowerThanOne_returnsObservableRequestWithEmptyList() {

        val result = sut.requestRecipePage(RecipeRequest("test", 0))

        val testObserver = TestObserver<RecipeRequest>()
        result.subscribe(testObserver)

        testObserver.assertComplete()
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        assertThat(testObserver.values()[0].results, CoreMatchers.`is`(emptyList()))

    }
}