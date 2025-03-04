package com.example.bodybalance.category.presentation

sealed interface CategoryState {

    data class Content(val category: List<String>) : CategoryState
    data object Loading : CategoryState
    data object Error : CategoryState
}