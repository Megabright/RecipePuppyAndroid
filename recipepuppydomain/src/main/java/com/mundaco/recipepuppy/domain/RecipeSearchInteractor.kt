package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.RecipeRequest
import io.reactivex.Observable


class RecipeSearchInteractor(private val recipeRepository: RecipeRepository): RecipeSearchUseCase {

    override fun searchRecipes(request: RecipeRequest): Observable<RecipeRequest> {
        return if(request.query.isEmpty()) {
            request.results = emptyList()
            Observable.just(request)
        }
        else recipeRepository.searchRecipes(request)
    }

}