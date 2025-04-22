package com.example.bodybalance.category.presentation.state

sealed interface CategoryScreenUiEvent {
    data object ChangeUser : CategoryScreenUiEvent
    data class DeleteVideo(val videoId: String) : CategoryScreenUiEvent
}