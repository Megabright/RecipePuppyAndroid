package com.mundaco.recipepuppy.network

import com.mundaco.recipepuppy.model.RecipeResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface RecipeApi {

    @GET("?q=ch")
    fun getRecipeResponse(): Observable<RecipeResponse>

}