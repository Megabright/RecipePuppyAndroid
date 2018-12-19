package com.mundaco.recipepuppy.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.mundaco.recipepuppy.data.model.RecipeQuery

@Dao
interface RecipeDao {
    @Query("SELECT * FROM RecipeQuery WHERE `query` = :query")
    fun search(query: String): RecipeQuery?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg queries: RecipeQuery)
}