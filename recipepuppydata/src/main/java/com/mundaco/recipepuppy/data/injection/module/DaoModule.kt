package com.mundaco.recipepuppy.data.injection.module

import android.arch.persistence.room.Room
import android.content.Context
import com.mundaco.recipepuppy.data.model.RecipeDao
import com.mundaco.recipepuppy.data.model.database.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
@Suppress("unused")
class DaoModule(val context: Context) {

    @Provides
    @Singleton
    internal fun provideRecipeDao(context: Context): RecipeDao {
        return Room.databaseBuilder(context , AppDatabase::class.java, "recipes").build().recipeDao()

    }

    @Provides
    @Singleton
    internal fun providesAppContext(): Context = context
}