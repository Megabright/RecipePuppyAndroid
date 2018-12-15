package com.mundaco.recipepuppy.data

import com.mundaco.recipepuppy.data.database.RecipeDao
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.network.RecipeApi
import com.mundaco.recipepuppy.data.utils.BASE_URL
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RecipeRepositoryImpl: RecipeRepository {

    private val recipeApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
        .create(RecipeApi::class.java)

    @Inject
    lateinit var recipeDao: RecipeDao


    override fun searchRecipes(query: String): Observable<List<Recipe>> {

        return Observable.fromCallable { recipeDao.search(query) }
            .concatMap { dbRecipeList ->
                if (dbRecipeList.isEmpty())
                    recipeApi.getRecipeResponse(query).concatMap { apiRecipeResponse ->
                        recipeDao.insertAll(*apiRecipeResponse.results.toTypedArray())
                        Observable.just(apiRecipeResponse.results)
                    }
                else
                    Observable.just(dbRecipeList)
            }
    }
}