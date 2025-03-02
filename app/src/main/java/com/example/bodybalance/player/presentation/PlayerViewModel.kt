package com.example.bodybalance.player.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    player: ExoPlayer
) : ViewModel() {

    private val _player = MutableStateFlow<ExoPlayer?>(player)
    val playerFlow: StateFlow<ExoPlayer?> = _player

    var currentPosition by mutableLongStateOf(0L)
    private var currentUrl: String? = null

    fun setVideoUrl(url: String) {
        if (currentUrl == url) return
        currentUrl = url

        _player.value?.apply {
            val mediaItem = MediaItem.fromUri(url)
            setMediaItem(mediaItem)
            prepare()
            seekTo(currentPosition)
        }
    }

    fun savePosition() {
        currentPosition = _player.value?.currentPosition ?: 0L
    }

    override fun onCleared() {
        super.onCleared()
        _player.value?.release()
    }
}