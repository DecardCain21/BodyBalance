package com.example.bodybalance.videoplayer.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bodybalance.videoplayer.presentation.VideoPlayerScreen

const val CATEGORY_ID = "category"
const val VIDEO_PLAYER_ROUTE = "video_player"
const val VIDEO_ID = "videos_playlist"

fun NavController.navigateToVideoPlayerScreen(categoryId: Int = -1, videoId: Int = -1) {
    navigate(route = "$VIDEO_PLAYER_ROUTE/${categoryId}/${videoId}") {
        launchSingleTop
    }
}

fun NavGraphBuilder.videoPlayerScreen(navigateBackToPlaylistScreen: () -> Unit) {
    composable(
        route = "$VIDEO_PLAYER_ROUTE/{$CATEGORY_ID}/{$VIDEO_ID}",
        arguments = listOf(
            navArgument(CATEGORY_ID) {
                type = NavType.IntType
                defaultValue = -1
            },
            navArgument(VIDEO_ID) {
                type = NavType.IntType
                defaultValue = -1
            }
        )
    ) { backStackEntry ->
        VideoPlayerScreen( // Возможно стоит подумать как сделать подругому
            categoryId = backStackEntry.arguments?.getInt(CATEGORY_ID)?:-1,
            videoId = backStackEntry.arguments?.getInt(VIDEO_ID)?:-1,
            navigateBackToPlaylistScreen = navigateBackToPlaylistScreen
        )
    }
}