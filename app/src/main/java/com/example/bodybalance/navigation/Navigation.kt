package com.example.bodybalance.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bodybalance.home.presenation.navigation.HOME_SCREEN_ROUTE
import com.example.bodybalance.home.presenation.navigation.homeScreen

@Composable
fun Navigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME_SCREEN_ROUTE) {
        homeScreen()
    }
}