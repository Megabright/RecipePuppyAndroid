package com.mundaco.recipepuppy.domain

interface ListScrolledToBottomUseCase {

    fun loadNextPageResults(page: Int)
}