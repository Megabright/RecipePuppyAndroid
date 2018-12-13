package com.mundaco.recipepuppy.base

import android.app.Application
import android.arch.lifecycle.ViewModel
import com.mundaco.recipepuppy.data.injection.module.DaoModule
import com.mundaco.recipepuppy.data.injection.module.NetworkModule
import com.mundaco.recipepuppy.injection.component.DaggerViewModelInjector
import com.mundaco.recipepuppy.injection.component.ViewModelInjector
import com.mundaco.recipepuppy.ui.recipe.RecipeListViewModel
import com.mundaco.recipepuppy.ui.recipe.RecipeViewModel

abstract class BaseViewModel(app: Application): ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .daoModule(DaoModule(app))
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is RecipeListViewModel -> injector.inject(this)
            is RecipeViewModel -> injector.inject(this)
        }
    }
}