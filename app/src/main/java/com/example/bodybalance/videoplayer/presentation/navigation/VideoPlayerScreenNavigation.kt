package com.example.bodybalance.videoplayer.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bodybalance.videoplayer.presentation.VideoPlayerScreen

const val CATEGORY_NAME = "category"
const val VIDEO_PLAYER_ROUTE = "videoplayer"

fun NavController.navigateToVideoPlayerScreen(category: String) {
    navigate(route = "$VIDEO_PLAYER_ROUTE/$category") {
        launchSingleTop
    }
}

fun NavGraphBuilder.videoPlayerScreen(navigateBackToPlaylistScreen: () -> Unit) {
    composable(
        route = "$VIDEO_PLAYER_ROUTE/{$CATEGORY_NAME}",
        arguments = listOf(navArgument(CATEGORY_NAME) { type = NavType.StringType })
    ) { backStackEntry ->
        VideoPlayerScreen(
            // Возможно стоит подумать как сделать подругому
            category = backStackEntry.arguments?.getString(CATEGORY_NAME) ?: "",
            navigateBackToPlaylistScreen = navigateBackToPlaylistScreen
        )
    }
}
