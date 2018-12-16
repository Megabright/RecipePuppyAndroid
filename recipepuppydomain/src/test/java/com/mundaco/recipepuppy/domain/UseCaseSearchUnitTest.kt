package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UseCaseSearchUnitTest {

    // Helpers

    class MockRecipeRepositoryImpl: RecipeRepository {

        // Singleton
        companion object {

            private var INSTANCE: MockRecipeRepositoryImpl? = null

            fun getInstance(): MockRecipeRepositoryImpl {
                if(INSTANCE == null) INSTANCE = MockRecipeRepositoryImpl()
                return INSTANCE!!
            }
        }

        override fun searchRecipes(query: String): Observable<List<Recipe>> {
            return Observable.fromArray(emptyList())
        }


    }

    val recipeRepository = MockRecipeRepositoryImpl.getInstance()


    val sut: RecipeSearchUseCase = RecipeSearchInteractor(recipeRepository)

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun search_whenEmptyQuery_ReturnsEmptyList() {

        val result = sut.searchRecipes("")

        result.subscribe(TestObserver<List<Recipe>>()
            .assertComplete()
            .assertNoErrors()
            //.assertValueCount(1)
            //.assertValue(emptyList())
        )


    }

    @After
    fun dispose() {

    }
}