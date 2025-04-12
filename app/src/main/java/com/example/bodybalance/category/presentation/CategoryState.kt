package com.example.bodybalance.category.presentation

import com.example.bodybalance.core.domain.models.Video

sealed interface CategoryState {

    data class Content(val category: List<String>, val playlist: List<Video>) : CategoryState
    data object Loading : CategoryState
    data object Error : CategoryState
}