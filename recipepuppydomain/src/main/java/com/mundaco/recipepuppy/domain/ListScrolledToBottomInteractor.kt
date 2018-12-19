package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.RecipeRequest
import io.reactivex.Observable

class ListScrolledToBottomInteractor(private val recipeRepository: RecipeRepository) : ListScrolledToBottomUseCase {

    override fun requestRecipePage(request: RecipeRequest): Observable<RecipeRequest> {
        return if(request.page < 1) {
            request.results = emptyList()
            Observable.just(request)
        } else recipeRepository.searchRecipes(request)
    }
}