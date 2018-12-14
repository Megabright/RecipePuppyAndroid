package com.mundaco.recipepuppy.ui.recipe

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.SearchView
import android.view.View
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.base.BaseViewModel
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.RecipeRepositoryDelegate
import com.mundaco.recipepuppy.data.model.Recipe

class RecipeListViewModel : BaseViewModel(), RecipeRepositoryDelegate {

    val recipeRepository = RecipeRepository(this)

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { recipeRepository.searchRecipes("") }

    val recipeListAdapter: RecipeListAdapter = RecipeListAdapter()

    val onQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            recipeRepository.searchRecipes(query!!)
            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
            recipeRepository.searchRecipes(query!!)
            return false
        }
    }

    init {
        recipeRepository.searchRecipes("")
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
        recipeRepository.dispose()
    }
}