package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.model.RecipeRequest
import io.reactivex.Observable

interface ListScrolledToBottomUseCase {

    fun requestRecipePage(request: RecipeRequest): Observable<RecipeRequest>
}