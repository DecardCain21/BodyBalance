package com.example.bodybalance.settings.presentation.settings.navigation

import androidx.navigation.NavController

interface NavigationHandler {
    fun getNavigationFunction(): NavController.() -> Unit
}