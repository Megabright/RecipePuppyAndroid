package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepositoryDelegate

interface RecipeSearchUseCase: RecipeRepositoryDelegate {

    fun searchRecipes(query: String)
}