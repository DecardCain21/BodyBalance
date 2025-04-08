package com.example.bodybalance.settings.presentation.about

sealed interface AboutAppScreenUiEvent {

    data class OpenContributorLink(val url: String) : AboutAppScreenUiEvent

    data class ShareTheApp(
        val title: String,
        val text: String
    ) : AboutAppScreenUiEvent
}

enum class Contributors(val url: String) {
    DEV_NIKITA("https://github.com/Fargo02"),
    DEV_MARAT("https://github.com/DecardCain21"),
    DESIGN_ANASTASIA("")
}