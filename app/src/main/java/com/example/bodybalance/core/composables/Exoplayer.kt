package com.example.bodybalance.core.composables

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.ui.PlayerView
import com.example.bodybalance.videoplayer.presentation.VideoPlayerViewModel

@Composable
fun ExoPlayer(
    modifier: Modifier = Modifier,
    url: String = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
    viewModel: VideoPlayerViewModel = hiltViewModel()
) {

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    LaunchedEffect(url) {
        viewModel.prepare(url)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> viewModel.savePlayerState()

                Lifecycle.Event.ON_RESUME -> viewModel.player.playWhenReady = true

                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f),
        factory = { context ->
            PlayerView(context).also {
                it.player = viewModel.player
            }
        },
        update = {
            it.player = viewModel.player
        },
    )
}