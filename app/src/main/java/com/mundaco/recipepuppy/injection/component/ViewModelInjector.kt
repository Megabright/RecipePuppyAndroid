package com.mundaco.recipepuppy.injection.component

import com.mundaco.recipepuppy.injection.module.NetworkModule
import com.mundaco.recipepuppy.ui.recipe.RecipeListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified RecipeListViewModel.
     * @param recipeListViewModel RecipeListViewModel in which to inject the dependencies
     */
    fun inject(recipeListViewModel: RecipeListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}