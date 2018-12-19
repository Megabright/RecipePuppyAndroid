package com.mundaco.recipepuppy.data

import android.util.Log
import com.mundaco.recipepuppy.data.database.RecipeDao
import com.mundaco.recipepuppy.data.model.RecipeRequest
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


    override fun searchRecipes(request: RecipeRequest): Observable<RecipeRequest> {

        return Observable.fromCallable {
            recipeDao.search(request.query) ?: request
        }.concatMap { dbRequest ->
                if(dbRequest.results == null) {
                    recipeApi.getRecipeResponse(request.query).concatMap { apiResponse ->
                        recipeDao.insertAll(RecipeRequest(request.query, request.page, apiResponse.results))
                        Log.d("REPOSITORY","Data gotten from the API")
                        dbRequest.results = apiResponse.results
                        Observable.just(dbRequest)
                    }
                }
                else {
                    Log.d("REPOSITORY","Data gotten from the local database.")
                    Observable.just(dbRequest)
                }

            }
    }
}