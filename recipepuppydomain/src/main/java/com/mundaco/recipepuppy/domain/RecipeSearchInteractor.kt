package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import io.reactivex.Observable


class RecipeSearchInteractor: RecipeSearchUseCase {

    private val recipeRepository = RecipeRepository()

    override fun searchRecipes(query: String): Observable<List<Recipe>> {
        // TODO: Return Observable with empty list instead
        return if(query.isEmpty()) Observable.empty()
        else recipeRepository.searchRecipes(query)
    }

    override fun getRepository(): RecipeRepository {
        return recipeRepository
    }
}