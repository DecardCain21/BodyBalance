package com.example.bodybalance.introduction.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.introduction.presentation.IntroductionScreen

const val INTRODUCTION_ROUTE = "introduction"

// Функция для навконтроллера , кладёт в граф/стек навигации
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
) {
    // Создание самой функции/экземпляра экрана
    composable(route = INTRODUCTION_ROUTE) {
        // Вложение функции перехода в следующий экран в экземпляр Introduction
        IntroductionScreen(navToPlaylist = navToPlaylist)
    }
}