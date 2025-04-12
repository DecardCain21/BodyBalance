package com.example.bodybalance.player.composable

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.view.View
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout.RESIZE_MODE_FIT
import androidx.media3.ui.PlayerView
import com.example.bodybalance.R
import com.example.bodybalance.ui.theme.White

@OptIn(UnstableApi::class)
@Composable
fun exoPlayer(
    context: Context,
    videoUrl: String,
    modifier: Modifier = Modifier,
    listener: Player.Listener? = null,
    shouldRequestFocus: () -> Unit = {},
    showButton: Boolean = false
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

    val controllerVisible = remember { mutableStateOf(true) }

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

    Box {
        AndroidView(
            modifier = modifier.aspectRatio(16 / 9f),
            factory = { _ ->
                PlayerView(context).apply {
                    setFullscreenButtonClickListener {
                        isLandscape = !isLandscape
                    }
                    resizeMode = RESIZE_MODE_FIT
                    player = exoPlayer

                    setControllerVisibilityListener(PlayerView.ControllerVisibilityListener { visibility ->
                        controllerVisible.value = visibility == View.VISIBLE
                    })
                }
            },
            update = { it.player = exoPlayer }
        )
        if (showButton && isLandscape) {
            AnimatedVisibility(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 16.dp, top = 12.dp),
                visible = controllerVisible.value,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(
                    modifier = modifier
                        .padding(horizontal = 24.dp)
                        .padding(vertical = 10.dp),
                    onClick = {
                        shouldRequestFocus()
                        isLandscape = !isLandscape
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White
                    ),
                    shape = RoundedCornerShape(19.dp)
                ) {
                    Text(
                        text = stringResource(R.string.enter_code),
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }

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