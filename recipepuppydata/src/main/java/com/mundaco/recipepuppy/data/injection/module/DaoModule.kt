package com.mundaco.recipepuppy.data.injection.module

import android.app.Application
import android.arch.persistence.room.Room
import com.mundaco.recipepuppy.data.model.RecipeDao
import com.mundaco.recipepuppy.data.model.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
// Safe here as we are dealing with a Dagger 2 module
@Suppress("unused")
class DaoModule(val application: Application) {

    @Provides
    @Singleton
    internal fun provideRecipeDao(): RecipeDao {
        return Room.databaseBuilder(application , AppDatabase::class.java, "recipes").build().recipeDao()

    }
}