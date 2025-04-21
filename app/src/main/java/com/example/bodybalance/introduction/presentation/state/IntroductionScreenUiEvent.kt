package com.example.bodybalance.introduction.presentation.state

sealed interface IntroductionScreenUiEvent {
    data class InputLogin(val text: String) : IntroductionScreenUiEvent
    data object Continue : IntroductionScreenUiEvent
}