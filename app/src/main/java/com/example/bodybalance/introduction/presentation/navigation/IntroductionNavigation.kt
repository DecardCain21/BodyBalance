package com.example.bodybalance.introduction.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.introduction.presentation.Introduction

const val INTRODUCTION_ROUTE = "introduction"

fun NavController.navigateToIntroductionScreen() {
    navigate(
        route = INTRODUCTION_ROUTE,
    ) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.introductionScreen() {
    composable(route = INTRODUCTION_ROUTE) {
        Introduction()
    }
}