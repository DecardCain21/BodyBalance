package com.example.bodybalance.settings.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.compose.composable
import com.example.bodybalance.settings.presentation.SettingsScreen

const val SETTINGS_NAME = "settings"

object SettingsNavigationHandler : NavigationHandler {
    override fun getNavigationFunction(): NavController.() -> Unit {
        return {
            navigateToSettingsScreen()
        }
    }

    private fun NavController.navigateToSettingsScreen() = navigate(SETTINGS_NAME) {
        navigate(
            route = SETTINGS_NAME
        ) {
            launchSingleTop
        }
    }

}

//
fun NavController.navigateBackToSettingsScreen() = navigate(SETTINGS_NAME) {
    popUpTo(SETTINGS_NAME) {
        inclusive = true
    }
}

fun NavController.navigateToSettingsScreen() {
    navigate(
        route = SETTINGS_NAME
    ) {
        launchSingleTop
    }
}

fun NavGraphBuilder.settingsScreen(
    navigateToAboutAppScreen: () -> Unit
) {
    composable(route = SETTINGS_NAME) {
        SettingsScreen(navigateToAboutAppScreen = navigateToAboutAppScreen)
    }
}