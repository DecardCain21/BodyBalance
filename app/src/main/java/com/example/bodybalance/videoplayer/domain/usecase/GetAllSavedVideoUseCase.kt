package com.example.bodybalance.videoplayer.domain.usecase

import com.example.bodybalance.core.domain.models.Video

internal interface GetAllSavedVideoUseCase {

    suspend operator fun invoke(): List<Video>
}