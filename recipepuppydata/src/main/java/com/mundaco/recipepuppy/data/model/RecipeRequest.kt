package com.mundaco.recipepuppy.data.model

import android.arch.persistence.room.Entity

@Entity(primaryKeys = ["query","page"])
class RecipeRequest(query: String = "", page: Int = 1, var results: List<Recipe>? = null) {

    var query: String = query
        set(value) {
            field = value
            results = null
        }
    var page: Int = page
        set(value) {
            field = value
            results = null
        }

}