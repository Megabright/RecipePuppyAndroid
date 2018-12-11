package com.mundaco.recipepuppy.data.network

import com.mundaco.recipepuppy.domain.model.RecipeResponse
import io.reactivex.Observable
import retrofit2.http.GET


interface RecipeApi {

    @GET("?q=ch")
    fun getRecipeResponse(): Observable<RecipeResponse>

}