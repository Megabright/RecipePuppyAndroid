package com.mundaco.recipepuppy.data.model

import android.arch.persistence.room.Entity

@Entity(primaryKeys = ["query","page"])
data class RecipeRequest(
    val query: String,
    val page: Int = 1,
    val results: List<Recipe>? = null
)