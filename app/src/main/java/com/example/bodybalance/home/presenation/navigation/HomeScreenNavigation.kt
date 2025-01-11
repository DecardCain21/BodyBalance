package com.example.bodybalance.home.presenation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.home.presenation.HomeScreen

const val HOME_SCREEN_ROUTE = "home_screen"

fun NavController.navigateToHomeScreen() = navigate(HOME_SCREEN_ROUTE) {
    popUpTo(HOME_SCREEN_ROUTE) {
        inclusive = true
    }
}

fun NavGraphBuilder.homeScreen() {
    composable(route = HOME_SCREEN_ROUTE) {
        HomeScreen()
    }
}