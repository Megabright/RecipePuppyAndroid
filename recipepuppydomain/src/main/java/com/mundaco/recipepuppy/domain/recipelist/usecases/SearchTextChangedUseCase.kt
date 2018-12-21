package com.mundaco.recipepuppy.domain.recipelist.usecases

interface SearchTextChangedUseCase {

    fun loadNewQueryResults(query: String)

}