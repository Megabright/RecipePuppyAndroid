package com.mundaco.recipepuppy.base

import android.arch.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    /*
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .daoModule(DaoModule)
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
    */
}