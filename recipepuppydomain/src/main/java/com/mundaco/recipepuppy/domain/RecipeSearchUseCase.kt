package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.model.Recipe
import io.reactivex.Observable

interface RecipeSearchUseCase {

    fun searchRecipes(query: String): Observable<List<Recipe>>

}