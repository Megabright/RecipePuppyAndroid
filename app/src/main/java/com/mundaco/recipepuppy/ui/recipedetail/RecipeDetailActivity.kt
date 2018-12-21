package com.mundaco.recipepuppy.ui.recipedetail

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.databinding.ActivityRecipeDetailBinding
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
