package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import io.reactivex.Observable


class RecipeSearchInteractor(private val recipeRepository: RecipeRepository): RecipeSearchUseCase {

    override fun searchRecipes(query: String): Observable<List<Recipe>> {
        return if(query.isEmpty()) Observable.fromArray<List<Recipe>>(emptyList())
        else recipeRepository.searchRecipes(query)
    }

}