package com.mundaco.recipepuppy.data.injection.module

import dagger.Module

@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
object DaoModule {

    /*
    @Provides
    @Singleton
    internal fun provideRecipeDao(context: Context): RecipeDao {
        return Room.databaseBuilder(context , AppDatabase::class.java, "recipes").build().recipeDao()

    }
    */
}