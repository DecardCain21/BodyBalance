package com.example.bodybalance.settings.presentation.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.settings.presentation.settings.SettingsScreenRoute

private const val SETTINGS_NAME = "settings"

internal fun NavController.navigateToSettingsScreen() {
    navigate(
        route = SETTINGS_NAME
    ) {
        launchSingleTop
    }
}

internal fun NavGraphBuilder.settingsScreen(
    navigateToAboutAppScreen: () -> Unit,
    navigateBackToPlaylistScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    composable(route = SETTINGS_NAME) {
        SettingsScreenRoute(
            navigateToAboutAppScreen = navigateToAboutAppScreen,
            navigateBackToPlaylistScreen = navigateBackToPlaylistScreen,
            navigateToHomeScreen = navigateToHomeScreen
        )
    }
}