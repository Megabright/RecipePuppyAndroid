package com.mundaco.recipepuppy.data

import android.util.Log
import com.mundaco.recipepuppy.data.database.RecipeDao
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeQuery
import com.mundaco.recipepuppy.data.network.RecipeApi
import com.mundaco.recipepuppy.data.utils.BASE_URL
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RecipeRepositoryImpl private constructor(): RecipeRepository {

    // Singleton
    companion object {

        private var INSTANCE: RecipeRepositoryImpl? = null

        fun getInstance(): RecipeRepositoryImpl {
            if(INSTANCE == null) INSTANCE = RecipeRepositoryImpl()
            return INSTANCE!!
        }
    }

    private val recipeApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .build()
        .create(RecipeApi::class.java)

    @Inject
    lateinit var recipeDao: RecipeDao


    override fun searchRecipes(query: String): Observable<List<Recipe>> {

        return Observable.fromCallable {
            recipeDao.search(query) ?: RecipeQuery(query, null)
        }.concatMap { dbRecipeQuery ->
                if(dbRecipeQuery.results == null) {
                    recipeApi.getRecipeResponse(query).concatMap { apiRecipeResponse ->
                        val apiRecipeQuery = RecipeQuery(query, apiRecipeResponse.results)
                        recipeDao.insertAll(apiRecipeQuery)
                        Log.d("REPOSITORY","Data gotten from the API")
                        Observable.just(apiRecipeResponse.results)
                    }
                }
                else {
                    Log.d("REPOSITORY","Data gotten from the local database.")
                    Observable.just(dbRecipeQuery.results)
                }

            }
    }
}