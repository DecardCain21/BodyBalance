package com.example.bodybalance.player.composable

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun rememberExoPlayer(
    context: Context,
    modifier: Modifier,
    videoUrl: String,
    listener: Player.Listener? = null
): ExoPlayer {
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
            if (listener != null) {
                addListener(listener)
            } // а вот и твой листенер будет
        }
    }
    var currentPosition by rememberSaveable { mutableLongStateOf(0L) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    var isLandscape by rememberSaveable { mutableStateOf(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) }
    val activity = context as Activity

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> currentPosition = exoPlayer.currentPosition
                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer.seekTo(currentPosition)
                    exoPlayer.playWhenReady
                }

                Lifecycle.Event.ON_DESTROY -> exoPlayer.release()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    HandleFullscreenMode(activity, isLandscape)

    AndroidView(
        modifier = modifier.aspectRatio(16 / 9f),
        factory = { context ->
            PlayerView(context).apply {
                setFullscreenButtonClickListener {
                    isLandscape = !isLandscape
                }
                resizeMode = RESIZE_MODE_ZOOM
                player = exoPlayer
            }
        },
        update = { it.player = exoPlayer }
    )

    return exoPlayer
}

@Composable
private fun HandleFullscreenMode(activity: Activity, isLandscape: Boolean) {
    DisposableEffect(isLandscape) {
        val windowInsetsController =
            WindowCompat.getInsetsController(activity.window, activity.window.decorView)

        if (isLandscape) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            WindowCompat.setDecorFitsSystemWindows(activity.window, false)
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            windowInsetsController.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            WindowCompat.setDecorFitsSystemWindows(activity.window, true)
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }

        onDispose {
            windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }
}

