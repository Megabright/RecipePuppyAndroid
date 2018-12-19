package com.mundaco.recipepuppy.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class RecipeRequest(
    @field:PrimaryKey
    val query: String,
    val results: List<Recipe>? = null
)