package com.mundaco.recipepuppy.domain

interface ListScrolledToBottomUseCase {

    fun requestNextPage(page: Int)
}