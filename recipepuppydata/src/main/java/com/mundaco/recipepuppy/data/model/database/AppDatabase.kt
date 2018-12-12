package com.mundaco.recipepuppy.data.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.mundaco.recipepuppy.data.model.RecipeDao
import com.mundaco.recipepuppy.datamodel.Recipe

@Database(entities = arrayOf(Recipe::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}