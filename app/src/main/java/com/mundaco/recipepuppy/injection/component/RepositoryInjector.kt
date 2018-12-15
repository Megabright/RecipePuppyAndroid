package com.mundaco.recipepuppy.injection.component

import com.mundaco.recipepuppy.data.RecipeRepositoryImpl
import com.mundaco.recipepuppy.injection.module.DaoModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DaoModule::class])
interface RepositoryInjector {

    fun inject(recipeRepository: RecipeRepositoryImpl)

    @Component.Builder
    interface Builder {
        fun build(): RepositoryInjector

        fun daoModule(daoModule: DaoModule): Builder
    }
}