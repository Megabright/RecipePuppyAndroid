package com.mundaco.recipepuppy.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.mundaco.recipepuppy.data.model.Recipe

@Database(entities = [Recipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}