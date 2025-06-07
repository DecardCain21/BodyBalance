package com.example.bodybalance.home.presentation.state

internal sealed interface HomeScreenUiEvent {
    data class InputLogin(val text: String) : HomeScreenUiEvent
    data object Enter : HomeScreenUiEvent
    data class GetLogin(val link:String) : HomeScreenUiEvent
    data object ClearAll : HomeScreenUiEvent
}