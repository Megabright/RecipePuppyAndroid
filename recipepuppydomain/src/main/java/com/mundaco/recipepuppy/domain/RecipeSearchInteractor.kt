package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.utils.SingletonHolder

class RecipeSearchInteractor constructor(delegate: RecipeSearchUseCaseDelegate) : RecipeSearchUseCase {

    var delegate: RecipeSearchUseCaseDelegate? = null

    private val recipeRepository = RecipeRepository(this)


    init {
        this.delegate = delegate
    }
    companion object : SingletonHolder<RecipeSearchInteractor, RecipeSearchUseCaseDelegate>(::RecipeSearchInteractor)

    override fun getRepository(): RecipeRepository {
        return recipeRepository
    }

    override fun searchRecipes(query: String) {
        recipeRepository.searchRecipes(query)
    }

    override fun onRetrieveRecipeListStart() {
        delegate!!.onRetrieveRecipeListStart()
    }

    override fun onRetrieveRecipeListFinish() {
        delegate!!.onRetrieveRecipeListFinish()
    }

    override fun onRetrieveRecipeListSuccess(recipeList: List<Recipe>) {
        delegate!!.onRetrieveRecipeListSuccess(recipeList)
    }

    override fun onRetrieveRecipeListError() {
        delegate!!.onRetrieveRecipeListError()
    }

    override fun dispose() {
        recipeRepository.dispose()
    }
}