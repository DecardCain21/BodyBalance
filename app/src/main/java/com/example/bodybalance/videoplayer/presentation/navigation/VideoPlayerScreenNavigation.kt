package com.example.bodybalance.videoplayer.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bodybalance.videoplayer.presentation.VideoPlayerScreen

const val ROUTE_ID = "route id"
const val ITEM_ID = "item id"
const val VIDEO_PLAYER_ROUTE = "video_player"

fun NavController.navigateToVideoPlayerScreen(routeId: VideoPlayerNavigateScreenId, itemId: Int) {
    navigate(route = "$VIDEO_PLAYER_ROUTE/${routeId.label}/${itemId}") {
        launchSingleTop
    }
}

fun NavGraphBuilder.videoPlayerScreen(navigateBackToPlaylistScreen: () -> Unit) {
    composable(
        route = "$VIDEO_PLAYER_ROUTE/{$ROUTE_ID}/{$ITEM_ID}",
        arguments = listOf(
            navArgument(ROUTE_ID) { type = NavType.StringType },
            navArgument(ITEM_ID) { type = NavType.IntType },
        )
    ) { backStackEntry ->
        val routeLabel = backStackEntry.arguments?.getString(ROUTE_ID) ?: ""
        val itemId = backStackEntry.arguments?.getInt(ITEM_ID) ?: 0
        VideoPlayerScreen( // Возможно стоит подумать как сделать подругому
            routeLabel = VideoPlayerNavigateScreenId.fromLabel(routeLabel),
            itemId = itemId,
            navigateBackToPlaylistScreen = navigateBackToPlaylistScreen
        )
    }
}

enum class VideoPlayerNavigateScreenId(val label: String) {
    CATEGORY("category"),
    PLAYLIST("playlist");

    companion object {
        fun fromLabel(label: String): VideoPlayerNavigateScreenId {
            return entries.find { it.label == label } ?: CATEGORY
        }
    }
}