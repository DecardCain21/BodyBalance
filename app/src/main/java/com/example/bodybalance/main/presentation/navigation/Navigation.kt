package com.example.bodybalance.main.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bodybalance.category.presentation.navigation.navigateToPlaylist
import com.example.bodybalance.category.presentation.navigation.playlistScreen
import com.example.bodybalance.home.presentation.navigation.HOME_ROUTE
import com.example.bodybalance.home.presentation.navigation.homeScreen
import com.example.bodybalance.home.presentation.navigation.navigateToHomeScreen
import com.example.bodybalance.introduction.presentation.navigation.INTRODUCTION_ROUTE
import com.example.bodybalance.introduction.presentation.navigation.introductionScreen
import com.example.bodybalance.introduction.presentation.navigation.navigateToIntroductionScreen
import com.example.bodybalance.settings.presentation.about.navigation.aboutAppScreen
import com.example.bodybalance.settings.presentation.about.navigation.navigateToAboutAppScreen
import com.example.bodybalance.settings.presentation.settings.navigation.navigateToSettingsScreen
import com.example.bodybalance.settings.presentation.settings.navigation.settingsScreen
import com.example.bodybalance.videoplayer.presentation.navigation.navigateToVideoPlayerScreen
import com.example.bodybalance.videoplayer.presentation.navigation.videoPlayerScreen

@Composable
fun Navigation(isAuthenticated: Boolean) {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) INTRODUCTION_ROUTE else HOME_ROUTE
    ) {

        homeScreen { navController.navigateToIntroductionScreen() }

        introductionScreen { navController.navigateToPlaylist() }

        playlistScreen(
            navigateToVideoPlayerScreen = { routeId, itemId ->
                navController.navigateToVideoPlayerScreen(
                    routeId = routeId,
                    itemId = itemId
                )
            },
            navigateToSettingsScreen = { navController.navigateToSettingsScreen() },
            navigateBackToIntroduction = { navController.popBackStack() },
            navigateToHomeScreen = { navController.navigateToHomeScreen() }
        )

        settingsScreen(
            navigateBackToPlaylistScreen = { navController.popBackStack() },
            navigateToAboutAppScreen = { navController.navigateToAboutAppScreen() },
            navigateToHomeScreen = { navController.navigateToHomeScreen() })

        aboutAppScreen(
            navigateBackToSettings = {
                navController.popBackStack()
            })

        videoPlayerScreen(navigateBackToPlaylistScreen = { navController.popBackStack() })
    }
}