package com.example.bodybalance.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bodybalance.home.presenation.navigation.HOME_ROUTE
import com.example.bodybalance.home.presenation.navigation.homeScreen
import com.example.bodybalance.introduction.presentation.navigation.introductionScreen
import com.example.bodybalance.introduction.presentation.navigation.navigateToIntroductionScreen
import com.example.bodybalance.playlist.presentation.navigation.navigateToPlaylist
import com.example.bodybalance.playlist.presentation.navigation.playlistScreen

@Composable
fun Navigation() {

    val navController: NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME_ROUTE) {

        homeScreen { navController.navigateToIntroductionScreen() }

        introductionScreen { navController.navigateToPlaylist() }

        playlistScreen()
    }
}