package com.mundaco.recipepuppy.data.model

import android.arch.persistence.room.Entity

@Entity(primaryKeys = ["query","page"])
data class RecipeRequest(
    var query: String,
    var page: Int = 1,
    var results: List<Recipe>? = null
) {
    fun new(query: String, page: Int = 1) {
        this.query = query
        this.page = page
        this.results = null
    }

    fun new(page: Int) {
        this.page = page
        this.results = null
    }
}