package com.example.bodybalance.introduction.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.composable.BasicButton
import com.example.bodybalance.player.composable.ExoPlayer

const val INTRODUCTION = "Introduction"

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun Introduction(
    modifier: Modifier = Modifier,
    navToPlaylist: () -> Unit = {},
    viewModel: IntroductionViewModel = hiltViewModel()
) {
    val isPreview = LocalInspectionMode.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentState = uiState

    LaunchedEffect(Unit) { viewModel.getVideo(INTRODUCTION) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isPreview) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3 / 4f)
                    .background(Color.Gray),
                contentAlignment = Alignment.Center
            ) {
                Text("ExoPlayer Placeholder", color = Color.White)
            }
        } else {
            when (currentState) {
                is IntroductionState.Content -> {
                    IntroductionScreenContent(
                        modifier = modifier,
                        videoUrl = currentState.videoUrl,
                        navToPlaylist = navToPlaylist
                    )
                }

                IntroductionState.Empty -> Unit
                IntroductionState.Loading -> IntroductionScreenLoading()
            }
        }
    }
}

@Composable
fun IntroductionScreenContent(
    modifier: Modifier = Modifier,
    videoUrl: String,
    navToPlaylist: () -> Unit,
) {
    val listener = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                Player.STATE_ENDED -> println("Video ended")
                Player.STATE_READY -> println("Video is ready")
                Player.STATE_BUFFERING -> println("Buffering...")
                Player.STATE_IDLE -> println("Idle")
            }
        }
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExoPlayer(url = videoUrl, listener = listener)
        BasicButton(
            text = "Done!", onClick = { navToPlaylist() }, modifier = Modifier
                .padding(50.dp)
        )
    }
}

@Composable
private fun IntroductionScreenLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Preview(showBackground = true)
@Composable
fun IntroductionPreview() {
    Introduction(
        modifier = Modifier.fillMaxSize(),
        navToPlaylist = { Log.d("Preview", "Test button clicked") }
    )
}