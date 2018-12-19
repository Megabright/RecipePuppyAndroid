package com.mundaco.recipepuppy.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.mundaco.recipepuppy.data.model.RecipeRequest
import com.mundaco.recipepuppy.data.utils.converters.RecipeResponseConverter

@Database(entities = [RecipeRequest::class], version = 1)
@TypeConverters(RecipeResponseConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}