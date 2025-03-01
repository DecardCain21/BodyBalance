package com.example.bodybalance.core.composables

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_ZOOM
import androidx.media3.ui.PlayerView
import com.example.bodybalance.PlayerViewModel

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayer(
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = hiltViewModel(),
    url: String
) {
    val localContext = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    var currentPosition by rememberSaveable { mutableLongStateOf(0L) }

    val exoPlayer by viewModel.player.collectAsState()
    //val exoPlayer = remember { buildExoPlayer(localContext) }

    val configuration = LocalConfiguration.current
    var isLandscape by rememberSaveable { mutableStateOf(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) }
    val activity = localContext as Activity

    LaunchedEffect(url) {
        viewModel.setVideoUrl(url)
    }

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> viewModel.savePosition()
                Lifecycle.Event.ON_RESUME -> {
                    exoPlayer?.seekTo(viewModel.currentPosition)
                    exoPlayer?.playWhenReady
                }

                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    HandleFullscreenMode(activity, isLandscape)

//    with(exoPlayer) {
//        val currentMediaUrl = currentMediaItem?.localConfiguration?.uri.toString()
//        if (currentMediaUrl != url) {
//            playWhenReady = false
//            val mediaItem = MediaItem.fromUri(url)
//            setMediaItem(mediaItem)
//            prepare()
//        }
//    }

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