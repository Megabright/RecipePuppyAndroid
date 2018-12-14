package com.mundaco.recipepuppy.data

import com.mundaco.recipepuppy.data.database.RecipeDao
import com.mundaco.recipepuppy.data.network.RecipeApi
import com.mundaco.recipepuppy.data.utils.SingletonHolder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeRepository constructor(delegate: RecipeRepositoryDelegate) {

    @Inject
    lateinit var recipeApi: RecipeApi

    @Inject
    lateinit var recipeDao: RecipeDao

    private lateinit var subscription: Disposable

    private var delegate: RecipeRepositoryDelegate? = null

    init {
        this.delegate = delegate
    }
    companion object : SingletonHolder<RecipeRepository, RecipeRepositoryDelegate>(::RecipeRepository)

    fun searchRecipes(query: String) {

        if(query.isEmpty()) {
            delegate!!.onRetrieveRecipeListStart()
            delegate!!.onRetrieveRecipeListSuccess(emptyList())
            delegate!!.onRetrieveRecipeListFinish()
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
            .doOnSubscribe { delegate!!.onRetrieveRecipeListStart() }
            .doOnTerminate { delegate!!.onRetrieveRecipeListFinish() }
            .subscribe(
                { result -> delegate!!.onRetrieveRecipeListSuccess(result) },
                { delegate!!.onRetrieveRecipeListError() }
            )
    }

    fun dispose() {
        subscription.dispose()
    }
}