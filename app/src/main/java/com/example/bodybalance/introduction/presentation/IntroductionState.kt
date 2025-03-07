package com.example.bodybalance.introduction.presentation

import com.example.bodybalance.core.domain.models.Video

sealed interface IntroductionState {
    data object Empty : IntroductionState
    data object Loading : IntroductionState
    data class Content(
        val videoUrl: String
    ) : IntroductionState
}

