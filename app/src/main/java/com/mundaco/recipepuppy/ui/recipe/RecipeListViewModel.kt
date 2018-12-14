package com.mundaco.recipepuppy.ui.recipe

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.SearchView
import android.view.View
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.base.BaseViewModel
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.domain.RecipeSearchInteractor
import com.mundaco.recipepuppy.domain.RecipeSearchUseCaseDelegate

class RecipeListViewModel : BaseViewModel(), RecipeSearchUseCaseDelegate {

    val recipeSearchUseCase = RecipeSearchInteractor(this)

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { recipeSearchUseCase.searchRecipes("") }

    val recipeListAdapter: RecipeListAdapter = RecipeListAdapter()

    val onQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            recipeSearchUseCase.searchRecipes(query!!)
            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
            recipeSearchUseCase.searchRecipes(query!!)
            return false
        }
    }

    init {
        recipeSearchUseCase.searchRecipes("")
    }

    override fun onRetrieveRecipeListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    override fun onRetrieveRecipeListFinish() {
        loadingVisibility.value = View.GONE
    }

    override fun onRetrieveRecipeListSuccess(recipeList: List<Recipe>) {
        recipeListAdapter.updateRecipeList(recipeList)
    }

    override fun onRetrieveRecipeListError() {
        errorMessage.value = R.string.recipe_error
    }

    override fun onCleared() {
        super.onCleared()
        recipeSearchUseCase.dispose()
    }
}