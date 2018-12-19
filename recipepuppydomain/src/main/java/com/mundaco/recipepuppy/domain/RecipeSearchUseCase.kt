package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.model.RecipeRequest
import io.reactivex.Observable

interface RecipeSearchUseCase {

    fun searchRecipes(request: RecipeRequest): Observable<RecipeRequest>

}