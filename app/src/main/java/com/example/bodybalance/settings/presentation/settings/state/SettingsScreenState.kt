package com.example.bodybalance.settings.presentation.settings.state

internal data class SettingsScreenState(
    val cacheSize: Long = 0L,
    val dialogData : DialogData = DialogData(),
    val downloadOnlyWifi: Boolean = false,
    val showDialog: Boolean = false,
    val navigateToHome: Boolean = false
)

internal data class DialogData(
    val title: String = "",
    val action: String = "",
    val onAction: () -> Unit = {}
)