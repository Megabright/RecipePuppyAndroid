package com.mundaco.recipepuppy.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.mundaco.recipepuppy.data.RecipeRepositoryImpl
import com.mundaco.recipepuppy.domain.recipelist.RecipeListInteractor
import com.mundaco.recipepuppy.injection.component.DaggerRepositoryInjector
import com.mundaco.recipepuppy.injection.module.DaoModule
import com.mundaco.recipepuppy.ui.recipedetail.RecipeViewModel
import com.mundaco.recipepuppy.ui.recipelist.RecipeListViewModel

class ViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {

            val recipeRepository = RecipeRepositoryImpl.getInstance()
            val interactor = RecipeListInteractor(recipeRepository)
            val recipeListViewModel = RecipeListViewModel(interactor)

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