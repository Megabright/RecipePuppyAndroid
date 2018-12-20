package com.mundaco.recipepuppy.domain

interface SearchTextChangedUseCase {

    fun sendNewRequest(query: String)

}