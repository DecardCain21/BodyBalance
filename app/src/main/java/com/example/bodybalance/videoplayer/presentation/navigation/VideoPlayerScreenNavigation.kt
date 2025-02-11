package com.example.bodybalance.videoplayer.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.playlist.presentation.navigation.PLAYLIST_ROUTE
import com.example.bodybalance.videoplayer.presentation.VideoPlayerScreen


const val VIDEO_PLAYER_ROUTE = "videoplayer"

fun NavController.navigateToVideoPlayerScreen() {
    navigate(route = VIDEO_PLAYER_ROUTE) {
        launchSingleTop
    }
}

fun NavGraphBuilder.videoPlayerScreen(

) {
    composable(route = VIDEO_PLAYER_ROUTE) {
        VideoPlayerScreen()
    }

}

/*
fun NavController.navigateToIntroductionScreen() {
    navigate(
        route = INTRODUCTION_ROUTE,
    ) {
        launchSingleTop = true
    }
}

// Создание экземляра экрана, самой функции Compose
fun NavGraphBuilder.introductionScreen(
    // Вложение функции для перехода на следующий экран
    navToPlaylist: () -> Unit,
    navToHomeScreen: () -> Unit
) {
    // Создание самой функции/экземпляра экрана
    composable(route = INTRODUCTION_ROUTE) {
        // Вложение функции перехода в следующий экран в экземпляр Introduction
        Introduction(navToPlaylist = navToPlaylist, navToHomeScreen = navToHomeScreen)
    }
}*/
