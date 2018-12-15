package com.mundaco.recipepuppy.ui.recipe

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.SearchView
import android.view.View
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.base.BaseViewModel
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.domain.RecipeSearchInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RecipeListViewModel : BaseViewModel() {

    lateinit var subscription: Disposable

    val recipeSearchUseCase = RecipeSearchInteractor()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { searchRecipes("") }

    val recipeListAdapter: RecipeListAdapter = RecipeListAdapter()

    val onQueryTextListener = object: SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String?): Boolean {
            searchRecipes(query!!)
            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
            searchRecipes(query!!)
            return false
        }
    }

    fun searchRecipes(query: String) {

        subscription = recipeSearchUseCase.searchRecipes(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveRecipeListStart() }
            .doOnTerminate { onRetrieveRecipeListFinish() }
            .subscribe(
                { result -> onRetrieveRecipeListSuccess(result) },
                { onRetrieveRecipeListError() }
            )
    }

    private fun onRetrieveRecipeListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveRecipeListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveRecipeListSuccess(recipeList: List<Recipe>) {
        recipeListAdapter.updateRecipeList(recipeList)
    }

    private fun onRetrieveRecipeListError() {
        errorMessage.value = R.string.recipe_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}