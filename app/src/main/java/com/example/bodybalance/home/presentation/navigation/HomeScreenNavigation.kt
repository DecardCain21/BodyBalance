package com.example.bodybalance.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.home.presentation.HomeScreen
import com.example.bodybalance.home.presentation.HomeScreenRoute

public const val HOME_ROUTE: String = "home"

public fun NavController.navigateToHomeScreen(): Unit = navigate(HOME_ROUTE) {
    popUpTo(HOME_ROUTE) {
        inclusive = true
    }
}

internal fun NavGraphBuilder.homeScreen(
    navigateToIntroductionScreen: () -> Unit,
) {
    composable(route = HOME_ROUTE) {
        HomeScreenRoute(navigateToIntroductionScreen = navigateToIntroductionScreen)
    }
}