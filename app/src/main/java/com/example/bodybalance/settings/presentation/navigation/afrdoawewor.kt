package com.example.bodybalance.settings.presentation.navigation

import androidx.navigation.NavController

interface NavigationHandler {
    fun getNavigationFunction(): NavController.() -> Unit
}