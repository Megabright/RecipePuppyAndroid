package com.mundaco.recipepuppy.ui.recipelist

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.SearchView
import android.view.View
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.base.BaseViewModel
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.domain.recipelist.RecipeListInteractor

class RecipeListViewModel(recipeRepository: RecipeRepository):
    BaseViewModel()
{
    var interactor = RecipeListInteractor(recipeRepository)

    val scrollPosition: MutableLiveData<Int> = MutableLiveData()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { interactor.sendCurrentRequest() }

    val onQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            interactor.loadNewQueryResults(query!!)
            scrollPosition.value = 0
            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
            interactor.loadNewQueryResults(query!!)
            scrollPosition.value = 0
            return false
        }
    }

    init {

        interactor.requestState.observeForever {

            when (it) {
                RecipeListInteractor.RequestState.START -> {
                    loadingVisibility.value = View.VISIBLE
                    errorMessage.value = null
                }
                RecipeListInteractor.RequestState.FINISH -> {
                    loadingVisibility.value = View.GONE
                }
                RecipeListInteractor.RequestState.ERROR -> {
                    errorMessage.value = R.string.recipe_error
                }
                else -> {
                }
            }
        }



    }

    fun showRecipe(recipe: Recipe) {
        interactor.showRecipe(recipe)
    }

    override fun onCleared() {
        super.onCleared()
        interactor.onCleared()
    }

    fun onEndOfPageReached(page: Int) {
        interactor.loadNextPageResults()
    }

}