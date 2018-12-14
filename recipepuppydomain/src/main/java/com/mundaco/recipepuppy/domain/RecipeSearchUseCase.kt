package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.RecipeRepositoryDelegate

interface RecipeSearchUseCase: RecipeRepositoryDelegate {

    fun searchRecipes(query: String)

    fun getRepository(): RecipeRepository

    fun dispose()
}