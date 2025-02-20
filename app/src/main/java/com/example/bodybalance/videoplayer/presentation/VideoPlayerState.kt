package com.example.bodybalance.videoplayer.presentation

import com.example.bodybalance.core.data.dto.ItemDto

sealed interface VideoPlayerState {

    data object Empty : VideoPlayerState
    data object Loading : VideoPlayerState
    data class Content(val videoUrl: String, val videoList: List<ItemDto>) : VideoPlayerState
}