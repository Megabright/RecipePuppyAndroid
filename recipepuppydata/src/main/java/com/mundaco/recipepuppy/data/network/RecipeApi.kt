package com.mundaco.recipepuppy.data.network

import com.mundaco.recipepuppy.data.model.RecipeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipeApi {

    @GET("?")
    fun getRecipeResponse(
        @Query("q") query: String,
        @Query("p") page: Int
    ): Observable<RecipeResponse>

}