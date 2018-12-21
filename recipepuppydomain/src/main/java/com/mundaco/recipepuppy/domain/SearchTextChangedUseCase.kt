package com.mundaco.recipepuppy.domain

interface SearchTextChangedUseCase {

    fun loadNewQueryResults(query: String)

}