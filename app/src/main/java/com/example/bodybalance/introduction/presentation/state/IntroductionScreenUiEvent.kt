package com.example.bodybalance.introduction.presentation.state

internal sealed interface IntroductionScreenUiEvent {
    data class InputLogin(val text: String) : IntroductionScreenUiEvent
    data object Continue : IntroductionScreenUiEvent
    data object ClearAll : IntroductionScreenUiEvent
}