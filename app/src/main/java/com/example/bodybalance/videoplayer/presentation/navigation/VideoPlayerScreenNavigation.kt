package com.example.bodybalance.videoplayer.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bodybalance.videoplayer.presentation.VideoPlayerScreen

const val CATEGORY_NAME = "category"
const val VIDEO_PLAYER_ROUTE = "video_player"

fun NavController.navigateToVideoPlayerScreen(categoryId: Int) {
    navigate(route = "$VIDEO_PLAYER_ROUTE/$categoryId") {
        launchSingleTop
    }
}

fun NavGraphBuilder.videoPlayerScreen() {
    composable(
        route = "$VIDEO_PLAYER_ROUTE/{$CATEGORY_NAME}",
        arguments = listOf(navArgument(CATEGORY_NAME) { type = NavType.IntType })
    ) { backStackEntry ->
        VideoPlayerScreen( // Возможно стоит подумать как сделать подругому
            categoryId = backStackEntry.arguments?.getInt(CATEGORY_NAME) ?: 0
        )
    }
}