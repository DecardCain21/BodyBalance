package com.example.bodybalance.videoplayer.presentation

import com.example.bodybalance.core.domain.models.Video

sealed interface VideoPlayerState {

    data object Empty : VideoPlayerState
    data object Loading : VideoPlayerState
    data class Content(
        val currentVideoUrl: String,
        val videoList: List<Video>
    ) : VideoPlayerState
}