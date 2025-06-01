package com.example.bodybalance.category.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.category.presentation.CategoryScreenRoute
import com.example.bodybalance.videoplayer.presentation.navigation.VideoPlayerNavigateScreenId

private const val PLAYLIST_ROUTE = "playlist"

public fun NavGraphBuilder.playlistScreen(
    navigateToVideoPlayerScreen: (VideoPlayerNavigateScreenId, Int) -> Unit,
    navigateToSettingsScreen: () -> Unit,
    navigateBackToIntroduction: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    composable(route = PLAYLIST_ROUTE) {
        CategoryScreenRoute(
            navigateToVideoPlayerScreen = navigateToVideoPlayerScreen,
            navigateToSettingsScreen = navigateToSettingsScreen,
            navigateBackToIntroduction = navigateBackToIntroduction,
            navigateToHomeScreen = navigateToHomeScreen
        )
    }
}

public fun NavController.navigateToPlaylist() {
    navigate(route = PLAYLIST_ROUTE) {
        launchSingleTop
    }
}