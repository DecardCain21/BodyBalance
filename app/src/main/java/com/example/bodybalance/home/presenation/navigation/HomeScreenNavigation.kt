package com.example.bodybalance.home.presenation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.home.presenation.HomeScreen

const val HOME_ROUTE = "home"

fun NavController.navigateToHomeScreen() = navigate(HOME_ROUTE) {
    popUpTo(HOME_ROUTE) {
        inclusive = true
    }
}

fun NavGraphBuilder.homeScreen(
    navigateToIntroductionScreen: () -> Unit,
) {
    composable(route = HOME_ROUTE) {
        HomeScreen(onSignInClick = navigateToIntroductionScreen)
    }
}