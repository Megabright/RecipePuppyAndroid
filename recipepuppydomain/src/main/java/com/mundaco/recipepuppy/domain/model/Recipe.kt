package com.mundaco.recipepuppy.domain.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Recipe(
    @field:PrimaryKey
    val title: String,
    val href: String,
    val ingredients: String,
    val thumbnail: String
)