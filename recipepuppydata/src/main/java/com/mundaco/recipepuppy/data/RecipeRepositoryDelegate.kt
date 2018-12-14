package com.mundaco.recipepuppy.data

import com.mundaco.recipepuppy.data.model.Recipe

interface RecipeRepositoryDelegate {

    fun onRetrieveRecipeListStart()

    fun onRetrieveRecipeListFinish()

    fun onRetrieveRecipeListSuccess(recipeList: List<Recipe>)

    fun onRetrieveRecipeListError()
}