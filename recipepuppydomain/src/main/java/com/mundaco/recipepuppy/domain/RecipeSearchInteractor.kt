package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeRequest
import io.reactivex.Observable


class RecipeSearchInteractor(private val recipeRepository: RecipeRepository): RecipeSearchUseCase {

    override fun searchRecipes(request: RecipeRequest): Observable<List<Recipe>> {
        return if(request.query.isEmpty()) Observable.fromArray<List<Recipe>>(emptyList())
        else recipeRepository.searchRecipes(request)
    }

}