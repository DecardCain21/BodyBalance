package com.example.bodybalance.category.presentation.state

import com.example.bodybalance.core.domain.models.Account

sealed interface CategoryScreenUiEvent {
    data class ChangeUser(val account: Account) : CategoryScreenUiEvent
    data class DeleteVideo(val videoId: String) : CategoryScreenUiEvent
}