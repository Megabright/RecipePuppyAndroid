package com.mundaco.recipepuppy.ui.recipelist

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.inputmethod.InputMethodManager
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.data.model.Recipe
import com.mundaco.recipepuppy.databinding.ActivityRecipeListBinding
import com.mundaco.recipepuppy.injection.ViewModelFactory
import com.mundaco.recipepuppy.ui.recipedetail.RecipeDetailActivity
import kotlinx.android.synthetic.main.activity_recipe_list.*


class RecipeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeListBinding
    private lateinit var viewModel: RecipeListViewModel

    private var errorSnackBar: Snackbar? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list)

        binding.recipeList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recipeList.adapter = RecipeListAdapter()
        binding.recipeList.setOnTouchListener { _,_ -> hideKeyboard();false }

        binding.scrollListener = EndlessRecyclerViewScrollListener(binding.recipeList.layoutManager!!)
        (binding.scrollListener as EndlessRecyclerViewScrollListener).endOfPageReached.observe(this, Observer {
            if(it != null) viewModel.onEndOfPageReached(it)
        })

        viewModel = ViewModelProviders.of(this, ViewModelFactory(applicationContext)).get(RecipeListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        viewModel.selectedRecipe.observe(this, Observer {
            val intent = Intent(applicationContext , RecipeDetailActivity::class.java )
            intent.putExtra("recipe", it)
            startActivity(intent)
        })

        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard()
    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackBar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackBar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackBar?.show()
    }

    private fun hideError() {
        errorSnackBar?.dismiss()
    }

    private fun hideKeyboard() {

        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(root_layout.windowToken, 0)

        root_layout.requestFocus()
    }

    fun onRecipeItemClicked(recipe: Recipe) {
        viewModel.showRecipe(recipe)
    }
}
