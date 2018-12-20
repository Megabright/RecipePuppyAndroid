package com.mundaco.recipepuppy.domain

import com.mundaco.recipepuppy.data.model.Recipe

interface ListItemClickedUseCase {

    fun showRecipe(recipe: Recipe)
}