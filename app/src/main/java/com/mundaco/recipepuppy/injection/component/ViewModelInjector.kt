package com.mundaco.recipepuppy.injection.component

import com.mundaco.recipepuppy.data.injection.module.DaoModule
import com.mundaco.recipepuppy.data.injection.module.NetworkModule
import com.mundaco.recipepuppy.ui.recipe.RecipeListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DaoModule::class])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified RecipeListViewModel.
     * @param recipeListViewModel RecipeListViewModel in which to inject the dependencies
     */
    fun inject(recipeListViewModel: RecipeListViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun daoModule(daoModule: DaoModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder

    }
}