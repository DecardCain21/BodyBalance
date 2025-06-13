package com.example.bodybalance.introduction.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.introduction.presentation.IntroductionScreenRoute

public const val INTRODUCTION_ROUTE: String = "introduction"

// Функция для навконтроллера , кладёт в граф/стек навигации
public fun NavController.navigateToIntroductionScreen() {
    navigate(INTRODUCTION_ROUTE) {
        launchSingleTop = true
        popUpTo(graph.startDestinationId) {
            inclusive = true
        }
    }
}

// Создание экземляра экрана, самой функции Compose
public fun NavGraphBuilder.introductionScreen(
    // Вложение функции для перехода на следующий экран
    navToPlaylist: () -> Unit,
) {
    // Создание самой функции/экземпляра экрана
    composable(route = INTRODUCTION_ROUTE) {
        // Вложение функции перехода в следующий экран в экземпляр Introduction
        IntroductionScreenRoute(navToPlaylist = navToPlaylist)
    }
}