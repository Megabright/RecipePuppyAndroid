package com.mundaco.recipepuppy.ui.recipe

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.databinding.ActivityRecipeDetailBinding
import com.mundaco.recipepuppy.datamodel.Recipe
import com.mundaco.recipepuppy.injection.ViewModelFactory

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private lateinit var viewModel: RecipeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(RecipeViewModel::class.java)

        val recipe = intent.getParcelableExtra<Recipe>("recipe")
        viewModel.bind(recipe)

        binding.viewModel = viewModel


    }
}
