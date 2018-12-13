package com.mundaco.recipepuppy.ui.recipe

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.mundaco.recipepuppy.R
import com.mundaco.recipepuppy.data.injection.module.NetworkModule
import com.mundaco.recipepuppy.databinding.ActivityRecipeListBinding
import com.mundaco.recipepuppy.injection.ViewModelFactory
import com.mundaco.recipepuppy.injection.component.DaggerViewModelInjector
import com.mundaco.recipepuppy.injection.component.ViewModelInjector
import com.mundaco.recipepuppy.data.injection.module.AppModule

class RecipeListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeListBinding
    private lateinit var viewModel: RecipeListViewModel

    private var errorSnackBar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .appModule(AppModule(this.application))
            .networkModule(NetworkModule)
            .build()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_list)
        binding.recipeList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(RecipeListViewModel::class.java)
        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })
        binding.viewModel = viewModel

        injector.inject(viewModel)

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
