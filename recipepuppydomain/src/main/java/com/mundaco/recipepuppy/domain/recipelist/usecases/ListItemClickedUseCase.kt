package com.mundaco.recipepuppy.domain.recipelist.usecases

import com.mundaco.recipepuppy.data.model.Recipe

interface ListItemClickedUseCase {

    fun showRecipe(recipe: Recipe)
}