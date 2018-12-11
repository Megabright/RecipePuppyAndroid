package com.mundaco.recipepuppy.data.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.mundaco.recipepuppy.domain.model.Recipe

@Dao
interface RecipeDao {
    @get:Query("SELECT * FROM recipe")
    val all: List<Recipe>

    @Insert
    fun insertAll(vararg recipes: Recipe)
}