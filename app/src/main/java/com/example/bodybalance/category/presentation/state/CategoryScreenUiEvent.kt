package com.example.bodybalance.category.presentation.state

import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.Video

internal sealed interface CategoryScreenUiEvent {
    data class ChangeUser(val account: Account) : CategoryScreenUiEvent
    data class DeleteVideo(val video: Video) : CategoryScreenUiEvent
    data class UpdateOrderPlaylistVideo(val id: Int, val order: Int) : CategoryScreenUiEvent
}