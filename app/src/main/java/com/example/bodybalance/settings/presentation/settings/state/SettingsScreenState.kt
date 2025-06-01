package com.example.bodybalance.settings.presentation.settings.state

internal data class SettingsScreenState(
    val cacheSize: Long = 0L,
    val downloadOnlyWifi: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val navigateToHome: Boolean = false
)