package com.example.bodybalance.videoplayer.presentation.state

import com.example.bodybalance.core.domain.models.Video

interface VideoPlayerScreenUiEvent {
    data class ChoiceVideo(val id: Double) : VideoPlayerScreenUiEvent
    data class DownloadVideo(val url: String, val fileName: String) : VideoPlayerScreenUiEvent
    data class AddToPlaylist(val video: Video) : VideoPlayerScreenUiEvent
}