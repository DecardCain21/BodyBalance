package com.example.bodybalance.settings.presentation.settings.state

import androidx.annotation.StringRes

internal data class SettingsScreenState(
    val cacheSize: Long = 0L,
    val dialogData : DialogData = DialogData(),
    val downloadOnlyWifi: Boolean = false,
    val showDialog: Boolean = false,
    val navigateToHome: Boolean = false
)

internal data class DialogData(
    @StringRes val title: Int? = null,
    @StringRes val action: Int? = null,
    @StringRes val description: Int? = null,
    val onAction: () -> Unit = {}
)