package com.mundaco.recipepuppy.domain.model

data class RecipeResponse(
    val title: String,
    val version: Float,
    val href: String,
    val results: List<Recipe>
)