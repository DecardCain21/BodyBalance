package com.example.bodybalance.category.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.category.presentation.Category

const val PLAYLIST_ROUTE = "playlist"

fun NavGraphBuilder.playlistScreen(
    navigateToVideoPlayerScreen: (String) -> Unit
) {
    composable(route = PLAYLIST_ROUTE) {
        Category(navigateToVideoPlayerScreen = navigateToVideoPlayerScreen)
    }
}

fun NavController.navigateToPlaylist() {
    navigate(route = PLAYLIST_ROUTE) {
        launchSingleTop
    }
}