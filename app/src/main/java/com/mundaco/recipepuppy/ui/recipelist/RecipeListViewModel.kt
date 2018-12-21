package com.mundaco.recipepuppy.ui.recipelist

import android.arch.lifecycle.MutableLiveData
import android.support.v7.widget.SearchView
import android.view.View
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.base.BaseViewModel
import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.model.RecipeRequest
import com.mundaco.recipepuppy.domain.recipelist.usecases.ListItemClickedUseCase
import com.mundaco.recipepuppy.domain.recipelist.usecases.ListScrolledToBottomUseCase
import com.mundaco.recipepuppy.domain.recipelist.usecases.RetryButtonClickedUseCase
import com.mundaco.recipepuppy.domain.recipelist.usecases.SearchTextChangedUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RecipeListViewModel(private val recipeRepository: RecipeRepository):
    BaseViewModel(),
    SearchTextChangedUseCase,
    ListItemClickedUseCase,
    ListScrolledToBottomUseCase,
    RetryButtonClickedUseCase
{
    private lateinit var subscription: Disposable

    var recipeRequest = RecipeRequest()

    val recipeList: MutableLiveData<List<Recipe>> = MutableLiveData()

    val scrollPosition: MutableLiveData<Int> = MutableLiveData()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { sendCurrentRequest() }

    val selectedRecipe: MutableLiveData<Recipe> = MutableLiveData()

    val onQueryTextListener = object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            loadNewQueryResults(query!!)
            return false
        }

        override fun onQueryTextChange(query: String?): Boolean {
            loadNewQueryResults(query!!)
            return false
        }
    }

    override fun showRecipe(recipe: Recipe) {
        selectedRecipe.value = recipe
    }

    override fun loadNewQueryResults(query: String) {

        recipeList.value = emptyList()

        scrollPosition.value = 0

        recipeRequest.query = query
        recipeRequest.page = 1

        sendCurrentRequest()
    }

    override fun loadNextPageResults() {

        recipeRequest.page += 1
        sendCurrentRequest()

    }

    override fun sendCurrentRequest() {

        if(recipeRequest.query.isEmpty()) {
            loadingVisibility.value = View.GONE
            errorMessage.value = null
        }
        else {
           subscription = recipeRepository.searchRecipes(recipeRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { onRetrieveRecipeListStart() }
                .doOnTerminate { onRetrieveRecipeListFinish() }
                .subscribe(
                    { result -> onRetrieveRecipeListSuccess(result) },
                    { error -> onRetrieveRecipeListError(error) }
                )
        }
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

    fun onEndOfPageReached(page: Int) {
        loadNextPageResults()
    }

}