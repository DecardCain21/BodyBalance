package com.example.bodybalance.category.presentation

sealed interface CategoryScreenUiEvent {
    data object ChangeUser : CategoryScreenUiEvent
    data class DeleteVideo(val videoId: String) : CategoryScreenUiEvent
}