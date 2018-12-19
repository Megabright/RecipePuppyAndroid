package com.mundaco.recipepuppy.ui.recipe

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.databinding.ActivityRecipeListBinding
import com.mundaco.recipepuppy.injection.ViewModelFactory

class RecipeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeListBinding
    private lateinit var viewModel: RecipeListViewModel

    private var errorSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list)
        binding.recipeList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recipeList.adapter = RecipeListAdapter()

        viewModel = ViewModelProviders.of(this, ViewModelFactory(applicationContext)).get(RecipeListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel

        // TODO: Turn this into a binding property
        val scrollListener = object: EndlessRecyclerViewScrollListener(binding.recipeList.layoutManager!!) {

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                // TODO: Notify the ViewModel
                Log.d("ScrollListener","onLoadMore(page: $page, totalItemsCount: $totalItemsCount)")
            }

        }
        binding.recipeList.addOnScrollListener(scrollListener)

    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackBar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackBar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackBar?.show()
    }

    private fun hideError(){
        errorSnackBar?.dismiss()
    }


}
