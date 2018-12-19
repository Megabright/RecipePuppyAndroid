package com.mundaco.recipepuppy.data

import com.mundaco.recipepuppy.data.model.RecipeRequest
import io.reactivex.Observable

interface RecipeRepository {

    fun searchRecipes(request: RecipeRequest): Observable<RecipeRequest>
}