package com.mundaco.recipepuppy.data.model

import android.arch.persistence.room.Entity

@Entity
data class RecipeResponse(
    val title: String,
    val version: Float,
    val href: String,
    val results: List<Recipe>
)