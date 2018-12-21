package com.mundaco.recipepuppy.domain.recipelist

import android.arch.lifecycle.MutableLiveData
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

class RecipeListInteractor(private val recipeRepository: RecipeRepository):
    SearchTextChangedUseCase,
    ListItemClickedUseCase,
    ListScrolledToBottomUseCase,
    RetryButtonClickedUseCase
{
    enum class RequestState {
        START,
        FINISH,
        ERROR
    }

    private lateinit var subscription: Disposable

    var recipeRequest = RecipeRequest()

    val recipeList: MutableLiveData<List<Recipe>> = MutableLiveData()

    val selectedRecipe: MutableLiveData<Recipe> = MutableLiveData()

    var requestState: MutableLiveData<RequestState> = MutableLiveData()


    override fun loadNewQueryResults(query: String) {

        recipeList.value = emptyList()

        recipeRequest.query = query
        recipeRequest.page = 1

        sendCurrentRequest()
    }

    override fun loadNextPageResults() {

        recipeRequest.page += 1
        sendCurrentRequest()
    }

    override fun sendCurrentRequest() {

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

    private fun onRetrieveRecipeListStart() {
        requestState.value = RequestState.START
    }

    private fun onRetrieveRecipeListFinish() {
        requestState.value = RequestState.FINISH
    }

    private fun onRetrieveRecipeListSuccess(recipeRequest: RecipeRequest) {
        recipeList.value = (recipeList.value!! + recipeRequest.results!!)
    }

    private fun onRetrieveRecipeListError(error: Throwable) {
        error.printStackTrace()
        requestState.value = RequestState.ERROR
    }

    override fun showRecipe(recipe: Recipe) {
        selectedRecipe.value = recipe
    }

    fun onCleared() {
        subscription.dispose()
    }
}