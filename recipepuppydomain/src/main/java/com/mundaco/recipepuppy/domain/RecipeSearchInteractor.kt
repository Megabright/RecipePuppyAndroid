package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe

class RecipeSearchInteractor : RecipeSearchUseCase {

    val recipeRepository = RecipeRepository(this)

    override fun searchRecipes(query: String) {

    }

    override fun onRetrieveRecipeListStart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRetrieveRecipeListFinish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRetrieveRecipeListSuccess(recipeList: List<Recipe>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRetrieveRecipeListError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}