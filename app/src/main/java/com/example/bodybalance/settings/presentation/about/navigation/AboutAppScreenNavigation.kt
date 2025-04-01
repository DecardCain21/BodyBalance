package com.example.bodybalance.settings.presentation.about.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.settings.presentation.about.AboutAppScreen

const val ABOUT_APP_NAME = "aboutapp"

fun NavController.navigateToAboutAppScreen(){
    navigate(
        route = ABOUT_APP_NAME
    ) {
        launchSingleTop
    }
}

fun NavGraphBuilder.aboutAppScreen(
    navigateBackToSettings: () -> Unit
){
    composable(route = ABOUT_APP_NAME) {
        AboutAppScreen(navigateBackToSettings = navigateBackToSettings)
    }
}