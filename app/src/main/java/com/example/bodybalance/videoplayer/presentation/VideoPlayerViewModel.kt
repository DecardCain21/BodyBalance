package com.example.bodybalance.videoplayer.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    val player: ExoPlayer
) : ViewModel() {

    private var currentPosition: Long = 0L

    fun prepare(url: String) {
        player.also {
            it.prepare()
            val mediaItem = MediaItem.fromUri(Uri.parse(url))
            it.setMediaItem(mediaItem)
            it.playWhenReady = true
            it.seekTo(currentPosition)
        }
    }

    fun savePlayerState() {
        currentPosition = player.currentPosition
        player.pause()
    }

    fun release() {
        player.release()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}
