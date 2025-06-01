package com.example.bodybalance.settings.presentation.settings.state

sealed interface SettingsScreenUiEvent {
    data object SingOut : SettingsScreenUiEvent
    data object ClearCache : SettingsScreenUiEvent
    data class ChangeVisibilitySingOutDialog(val flag: Boolean) : SettingsScreenUiEvent
    data class ChangeDownloadSettings(val flag: Boolean) : SettingsScreenUiEvent
}