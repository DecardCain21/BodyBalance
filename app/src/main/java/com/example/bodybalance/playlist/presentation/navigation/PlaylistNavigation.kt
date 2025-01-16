package com.example.bodybalance.playlist.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.playlist.presentation.Playlist

const val PLAYLIST_ROUTE = "playlist"

fun NavGraphBuilder.playlistScreen() {
    composable(route = PLAYLIST_ROUTE) {
        Playlist()
    }
}

fun NavController.navigateToPlaylist() {
    navigate(
        route = PLAYLIST_ROUTE
    ) {
        launchSingleTop
    }
}