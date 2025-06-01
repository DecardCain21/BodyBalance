package com.example.bodybalance.core.composable

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
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
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.ui.theme.White

@OptIn(UnstableApi::class)
@Composable
public fun exoPlayer(
    context: Context,
    video: Video,
    modifier: Modifier = Modifier,
    listener: Player.Listener? = null,
    showButton: Boolean = false,
    shouldRequestFocus: () -> Unit = {}
): ExoPlayer {

    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context).build().apply {
            playWhenReady = false
            if (listener != null) {
                addListener(listener)
            }
        }
    }

    var currentPosition by rememberSaveable { mutableLongStateOf(0L) }

    LaunchedEffect(video.url) {
        exoPlayer.apply {
            val mediaItem = MediaItem.fromUri(Uri.parse(video.url))
            setMediaItem(mediaItem)
            seekTo(currentPosition)
            prepare()
        }
        currentPosition = 0L
    }

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

    BoxWithConstraints {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        val playerHeight = if (isLandscape) screenHeight else screenWidth / (16f / 9f)

        Box(
            modifier = modifier
                .zIndex(1f)
                .width(screenWidth)
                .height(playerHeight)
                .align(Alignment.Center)
        ) {
            VideoPlayer(
                exoPlayer = exoPlayer,
                context = context,
                onFullscreenClick = { isLandscape = !isLandscape },
                onControllerVisibilityChange = { visible ->
                    controllerVisible.value = visible
                }
            )

            if (isLandscape) {
                VideoPlayerControls(
                    controllerVisible = controllerVisible.value,
                    videoName = video.name,
                    showButton = showButton,
                    onButtonClick = {
                        shouldRequestFocus()
                        isLandscape = !isLandscape
                    }
                )
            }
        }
    }

    return exoPlayer
}

@OptIn(UnstableApi::class)
@Composable
private fun VideoPlayer(
    exoPlayer: ExoPlayer,
    context: Context,
    onFullscreenClick: () -> Unit,
    onControllerVisibilityChange: (Boolean) -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            PlayerView(context).apply {
                setFullscreenButtonClickListener { onFullscreenClick() }
                resizeMode = RESIZE_MODE_FIT
                player = exoPlayer

                setControllerVisibilityListener(
                    PlayerView.ControllerVisibilityListener { visibility ->
                        onControllerVisibilityChange(visibility == View.VISIBLE)
                    }
                )
            }
        },
        update = { it.player = exoPlayer }
    )
}

@Composable
private fun VideoPlayerControls(
    controllerVisible: Boolean,
    videoName: String,
    showButton: Boolean,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.weight(1f)) {
            VideoTitle(
                text = videoName,
                visible = controllerVisible
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        if (showButton) {
            EnterCodeButton(
                visible = controllerVisible,
                onClick = onButtonClick
            )
        }
    }
}

@Composable
private fun VideoTitle(
    text: String,
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Text(
            text = text,
            color = White,
            fontSize = 22.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun EnterCodeButton(
    visible: Boolean,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = White),
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