package com.mundaco.recipepuppy.data.model

data class RecipeResponse(
    val title: String,
    val version: Float,
    val href: String,
    val results: List<Recipe>
)