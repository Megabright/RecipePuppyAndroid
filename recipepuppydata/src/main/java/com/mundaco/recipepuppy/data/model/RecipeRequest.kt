package com.mundaco.recipepuppy.data.model

import android.arch.persistence.room.Entity

@Entity(primaryKeys = ["query","page"])
data class RecipeRequest(
    var query: String,
    var page: Int = 1,
    val results: List<Recipe>? = null
)