package com.mundaco.recipepuppy.ui.recipe

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.base.BaseViewModel
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeRequest
import com.mundaco.recipepuppy.domain.RecipeSearchInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RecipeListViewModel(recipeRepository: RecipeRepository) : BaseViewModel(), EndlessRecyclerViewScrollListenerDelegate {

    private lateinit var subscription: Disposable

    private val recipeSearchUseCase = RecipeSearchInteractor(recipeRepository)

    private var recipeRequest = RecipeRequest("")

    val recipeList: MutableLiveData<List<Recipe>> = MutableLiveData()

    val scrollPosition: MutableLiveData<Int> = MutableLiveData()
    lateinit var scrollListener: EndlessRecyclerViewScrollListener

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { searchRecipes() }

    val onQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            newSearchRequest(query!!)
            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
            newSearchRequest(query!!)
            return false
        }
    }


    private fun newSearchRequest(query: String) {

        recipeList.value = emptyList()

        scrollPosition.value = 0
        scrollListener.resetState()


        recipeRequest.new(query)

        searchRecipes()
    }

    private fun newPageRequest(page: Int) {

        recipeRequest.new(page)

        searchRecipes()

    }


    private fun searchRecipes() {

        subscription = recipeSearchUseCase.searchRecipes(recipeRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveRecipeListStart() }
            .doOnTerminate { onRetrieveRecipeListFinish() }
            .subscribe(
                { result -> onRetrieveRecipeListSuccess(result) },
                { error -> onRetrieveRecipeListError(error) }
            )
    }

    private fun onRetrieveRecipeListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveRecipeListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveRecipeListSuccess(recipeRequest: RecipeRequest) {
        recipeList.value = (recipeList.value!! + recipeRequest.results!!)
    }

    private fun onRetrieveRecipeListError(error: Throwable) {
        error.printStackTrace()
        errorMessage.value = R.string.recipe_error
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
        newPageRequest(page + 1)
    }

}