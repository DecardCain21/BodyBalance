package com.example.bodybalance.player.composable

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.view.View
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun exoPlayer(
    modifier: Modifier,
    context: Context,
    videoUrl: String,
    listener: Player.Listener? = null
): ExoPlayer {

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = false
            if (listener != null) {
                addListener(listener)
            }
        }
    }
    var currentPosition by rememberSaveable { mutableLongStateOf(0L) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val configuration = LocalConfiguration.current
    var isLandscape by rememberSaveable {
        mutableStateOf(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    }
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
        modifier = if (isLandscape) modifier.fillMaxSize() else modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f),
        factory = { _ ->
            PlayerView(context).apply {
                setFullscreenButtonClickListener {
                    isLandscape = !isLandscape
                }
                resizeMode = RESIZE_MODE_FIT
                player = exoPlayer
            }
        },
        update = { it.player = exoPlayer }
    )

    return exoPlayer
}

@Composable
private fun HandleFullscreenMode(activity: Activity, isLandscape: Boolean) {
    LaunchedEffect(isLandscape) {
        activity.window?.apply {
            if (isLandscape) {
                addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
            } else {
                clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }

        activity.requestedOrientation = if (isLandscape) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}