package com.example.bodybalance.introduction.presentation.navigation

import androidx.compose.runtime.Composition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bodybalance.Test
import com.example.bodybalance.introduction.presentation.Introduction

const val INTRODUCTION_ROUTE = "introduction"

const val TEST_ROUTE = "test"

fun NavController.navigateToIntroductionScreen() {
    navigate(
        route = INTRODUCTION_ROUTE,
    ) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.test() {
    composable(route = TEST_ROUTE) {
        Test()
    }
}

fun NavController.navToTest() {
    navigate(
        route = TEST_ROUTE
    ) {
        launchSingleTop
    }
}

fun NavGraphBuilder.introductionScreen(
    navToTest: () -> Unit,
) {
    composable(route = INTRODUCTION_ROUTE) {
        Introduction(test = navToTest)
    }
}