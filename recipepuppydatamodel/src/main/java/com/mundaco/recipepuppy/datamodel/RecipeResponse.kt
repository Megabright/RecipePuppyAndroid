package com.mundaco.recipepuppy.datamodel

data class RecipeResponse(
    val title: String,
    val version: Float,
    val href: String,
    val results: List<Recipe>
)