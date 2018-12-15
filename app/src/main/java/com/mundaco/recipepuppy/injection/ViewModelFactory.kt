package com.mundaco.recipepuppy.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.mundaco.recipepuppy.data.RecipeRepositoryImpl
import com.mundaco.recipepuppy.injection.component.DaggerRepositoryInjector
import com.mundaco.recipepuppy.injection.module.DaoModule
import com.mundaco.recipepuppy.ui.recipe.RecipeListViewModel
import com.mundaco.recipepuppy.ui.recipe.RecipeViewModel

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {

            val recipeRepository = RecipeRepositoryImpl()
            val recipeListViewModel = RecipeListViewModel(recipeRepository)

            // Dependency Injection
            DaggerRepositoryInjector
                .builder()
                .daoModule(DaoModule(context))
                .build()
                .inject(recipeRepository)

            @Suppress("UNCHECKED_CAST")
            return recipeListViewModel as T

        } else if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}