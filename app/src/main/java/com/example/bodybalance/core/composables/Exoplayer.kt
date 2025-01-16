package com.example.bodybalance.core.composables

import android.content.Context
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
fun ExoPlayer(
    modifier: Modifier = Modifier,
    url: String = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
) {

    val localContext = LocalContext.current

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    var currentPosition by rememberSaveable { mutableLongStateOf(0L) }

    val exoPlayer = remember { createConfiguredExoPlayer(localContext, url, currentPosition) }

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    currentPosition = exoPlayer.currentPosition
                }

                Lifecycle.Event.ON_RESUME -> exoPlayer.playWhenReady = true

                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f),
        factory = { context ->
            PlayerView(context).also {
                it.player = exoPlayer
            }
        },
        update = {
            it.player = exoPlayer
        },
    )
}

private fun createConfiguredExoPlayer(
    context: Context,
    url: String,
    startPosition: Long
): ExoPlayer {
    return ExoPlayer.Builder(context).build().apply {
        val mediaItem = MediaItem.fromUri(url)
        setMediaItem(mediaItem)
        playWhenReady = true
        seekTo(startPosition)
        prepare()
    }
}