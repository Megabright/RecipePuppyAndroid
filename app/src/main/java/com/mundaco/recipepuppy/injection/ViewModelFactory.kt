package com.mundaco.recipepuppy.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import com.mundaco.recipepuppy.ui.recipe.RecipeListViewModel
import com.mundaco.recipepuppy.ui.recipe.RecipeViewModel

class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeListViewModel::class.java)) {
            //val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "recipes").build()
            @Suppress("UNCHECKED_CAST")
            return RecipeListViewModel() as T
        } else if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecipeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}