package com.mundaco.recipepuppy.data.utils.converters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mundaco.recipepuppy.data.model.Recipe


class RecipeResponseConverter {

    @TypeConverter
    fun fromString(value: String): List<Recipe> {
        val listType = object : TypeToken<List<Recipe>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromRecipeResponse(recipeList: List<Recipe>): String {
        val gson = Gson()
        return gson.toJson(recipeList)
    }
}
