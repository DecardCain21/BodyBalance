package com.example.bodybalance.playlist.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bodybalance.core.composables.ExoPlayer

@Composable
fun Playlist(modifier: Modifier = Modifier) {
    Column {
        ExoPlayer()
    }
}