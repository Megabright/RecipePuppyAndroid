package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.RecipeRequest
import io.reactivex.Observable

class ListScrolledToBottomInteractor(private val recipeRepository: RecipeRepository) : ListScrolledToBottomUseCase {

    override fun requestRecipePage(request: RecipeRequest): Observable<RecipeRequest> {
        return recipeRepository.searchRecipes(request)
    }
}