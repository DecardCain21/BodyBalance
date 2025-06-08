package com.example.bodybalance.settings.presentation.about

internal sealed interface AboutAppScreenUiEvent {

    data class OpenContributorLink(val url: String) : AboutAppScreenUiEvent

    data class ShareTheApp(
        val title: String,
        val text: String
    ) : AboutAppScreenUiEvent
}

internal enum class Contributors(val url: String) {
    DEV_NIKITA("https://github.com/Fargo02"),
    DEV_MARAT("https://github.com/DecardCain21"),
    DEV_SERGEY("https://github.com/langowen"),
    DESIGN_ANASTASIA("https://t.me/AnastasiaGee")
}