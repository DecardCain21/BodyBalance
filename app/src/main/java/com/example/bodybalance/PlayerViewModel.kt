package com.example.bodybalance

import android.content.Context
import androidx.annotation.OptIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.bodybalance.core.util.ExoPlayerCache
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _player = MutableStateFlow<ExoPlayer?>(null)
    val player: StateFlow<ExoPlayer?> = _player

    var currentPosition by mutableLongStateOf(0L)
    private var currentUrl: String? = null

    init {
        _player.value = buildExoPlayer(context)
    }

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

@OptIn(UnstableApi::class)
fun buildExoPlayer(context: Context): ExoPlayer {
    return ExoPlayer.Builder(context)
        .setMediaSourceFactory(
            DefaultMediaSourceFactory(
                ExoPlayerCache.getCacheDataSourceFactory(context)
            )
        )
        .build()
}