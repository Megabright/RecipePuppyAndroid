package com.mundaco.recipepuppy.data.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mundaco.recipepuppy.datamodel.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe WHERE title LIKE :query")
    fun search(query: String): List<Recipe>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg recipes: Recipe)
}