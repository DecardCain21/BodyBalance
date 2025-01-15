package com.example.bodybalance.introduction.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.bodybalance.core.composables.BasicButton
import com.example.bodybalance.core.composables.ExoPlayer
import com.example.bodybalance.core.composables.Player
import com.example.bodybalance.videoplayer.presentation.VideoPlayerViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Introduction(
    modifier: Modifier = Modifier,
    test: () -> Unit = {}
) {

    Column {
        // ExoPlayer()
        Player(
            video = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            playingIndex = remember { mutableIntStateOf(0) }
        )
        BasicButton(text = "to test", onClick = { test() })
    }
}