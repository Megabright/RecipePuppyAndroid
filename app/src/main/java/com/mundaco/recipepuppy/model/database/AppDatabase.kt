package com.mundaco.recipepuppy.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.mundaco.recipepuppy.model.Recipe
import com.mundaco.recipepuppy.model.RecipeDao

@Database(entities = arrayOf(Recipe::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): RecipeDao
}