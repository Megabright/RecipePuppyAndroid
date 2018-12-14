package com.mundaco.recipepuppy.injection.component

import com.mundaco.recipepuppy.data.RecipeRepository
import com.mundaco.recipepuppy.data.injection.module.DaoModule
import com.mundaco.recipepuppy.data.injection.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DaoModule::class])
interface ViewModelInjector {

    fun inject(recipeRepository: RecipeRepository)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun daoModule(daoModule: DaoModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder

    }
}