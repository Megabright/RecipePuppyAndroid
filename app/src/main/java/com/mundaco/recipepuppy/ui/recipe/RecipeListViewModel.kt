package com.mundaco.recipepuppy.ui.recipe

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.base.BaseViewModel
import com.mundaco.recipepuppy.data.model.RecipeDao
import com.mundaco.recipepuppy.data.network.RecipeApi
import com.mundaco.recipepuppy.datamodel.Recipe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeListViewModel(private val recipeDao: RecipeDao) : BaseViewModel() {
    @Inject
    lateinit var recipeApi: RecipeApi

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    private lateinit var subscription: Disposable

    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadRecipes("") }

    val recipeListAdapter: RecipeListAdapter = RecipeListAdapter()

    init {
        loadRecipes("")
    }


    fun loadRecipes(query: String) {

        if(query.isEmpty()) {
            onRetrieveRecipeListStart()
            onRetrieveRecipeListSuccess(emptyList())
            onRetrieveRecipeListFinish()
            return
        }

        subscription = Observable.fromCallable { recipeDao.search(query) }
            .concatMap { dbRecipeList ->
                if (dbRecipeList.isEmpty())
                    recipeApi.getRecipeResponse(query).concatMap {
                            apiRecipeResponse ->
                        recipeDao.insertAll(*apiRecipeResponse.results.toTypedArray())
                        Observable.just(apiRecipeResponse.results)
                    }
                else
                    Observable.just(dbRecipeList)
            }
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