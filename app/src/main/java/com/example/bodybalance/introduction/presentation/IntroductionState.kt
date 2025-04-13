package com.example.bodybalance.introduction.presentation

import com.example.bodybalance.core.domain.models.Video

data class IntroductionScreenState(
    val inputValue: Input,
    val videoState: IntroductionPlayerState,
    val buttonIsEnabled: Boolean = false,
    val supportText: String = SupportTextIntroduction.ENTER_LOGIN.message
) {
    sealed interface Input {
        data object Empty : Input
        data class Text(val value: String) : Input
    }

    sealed interface IntroductionPlayerState {
        data object Empty : IntroductionPlayerState
        data object Loading : IntroductionPlayerState
        data class Content(val video: Video) : IntroductionPlayerState
    }
}

enum class SupportTextIntroduction(val message: String) {
    INVALID_LOGIN("Неверное кодовое слово"),
    ENTER_LOGIN("Введите кодовое слово")
}