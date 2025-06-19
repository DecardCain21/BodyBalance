package com.example.bodybalance.introduction.presentation.state

import com.example.bodybalance.core.domain.models.Video

internal data class IntroductionScreenState(
    val validateLogin:Boolean = false,
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
        data class Content(val video: Video = Video.emptyVideo(1)) : IntroductionPlayerState
    }

    companion object {
        fun emptyState(): IntroductionScreenState =
            IntroductionScreenState(
                inputValue = Input.Empty,
                videoState = IntroductionPlayerState.Content(),
            )
    }
}

internal enum class SupportTextIntroduction(val message: String) {
    INVALID_LOGIN("Неверное кодовое слово"),
    ENTER_LOGIN("Введите кодовое слово"),
    VALID_LOGIN("")
}