package com.example.bodybalance.introduction.presentation


data class IntroductionScreenState(
    val inputValue: Input,
    val videoState: IntroductionPlayerState,
    val buttonIsEnabled: Boolean = false
) {
    sealed interface Input {
        data object Empty : Input
        data class Text(val value: String) : Input
    }

    sealed interface IntroductionPlayerState {
        data object Empty : IntroductionPlayerState
        data object Loading : IntroductionPlayerState
        data class Content(
            val videoUrl: String,
        ) : IntroductionPlayerState
    }
}

enum class SupportTextHome(val message: String) {
    INVALID_LOGIN("Неверное кодовое слово"),
    ENTER_LOGIN("Введите кодовое слово")
}

