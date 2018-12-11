package com.mundaco.recipepuppy.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface RecipeDao {
    @get:Query("SELECT * FROM recipe")
    val all: List<Recipe>

    @Insert
    fun insertAll(vararg recipes: Recipe)
}