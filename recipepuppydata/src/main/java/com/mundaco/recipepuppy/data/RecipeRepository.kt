package com.mundaco.recipepuppy.data

import com.mundaco.recipepuppy.data.model.Recipe
import io.reactivex.Observable

interface RecipeRepository {

    fun searchRecipes(query: String): Observable<List<Recipe>>
}