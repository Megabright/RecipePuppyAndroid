package com.mundaco.recipepuppy.injection.module

import android.arch.persistence.room.Room
import android.content.Context
import com.mundaco.recipepuppy.data.database.AppDatabase
import com.mundaco.recipepuppy.data.database.RecipeDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
@Suppress("unused")
class DaoModule(val context: Context) {

    @Provides
    @Singleton
    internal fun provideRecipeDao(): RecipeDao {
        return Room.databaseBuilder(context , AppDatabase::class.java, "queries").build().recipeDao()

    }

    @Provides
    @Singleton
    internal fun providesAppContext(): Context = context
}