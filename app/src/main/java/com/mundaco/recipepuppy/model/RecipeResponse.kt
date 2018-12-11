package com.mundaco.recipepuppy.model

data class RecipeResponse(
    val title: String,
    val version: Float,
    val href: String,
    val results: List<Recipe>
)