package com.mundaco.recipepuppy.base

import android.arch.lifecycle.ViewModel
import com.mundaco.recipepuppy.injection.component.DaggerViewModelInjector
import com.mundaco.recipepuppy.injection.component.ViewModelInjector
import com.mundaco.recipepuppy.injection.module.NetworkModule
import com.mundaco.recipepuppy.ui.recipe.RecipeListViewModel

abstract class BaseViewModel: ViewModel(){

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
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
            //is RecipeViewModel -> injector.inject(this)
        }
    }
}