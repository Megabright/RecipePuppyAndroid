package com.mundaco.recipepuppy.data

import com.mundaco.recipepuppy.data.database.RecipeDao
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.data.network.RecipeApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RecipeRepository {

    @Inject
    lateinit var recipeApi: RecipeApi

    @Inject
    lateinit var recipeDao: RecipeDao

    fun searchRecipes(query: String): Observable<List<Recipe>> {

        return Observable.fromCallable { recipeDao.search(query) }
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
    }
}