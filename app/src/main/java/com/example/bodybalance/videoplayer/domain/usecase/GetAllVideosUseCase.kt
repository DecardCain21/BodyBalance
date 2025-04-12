package com.example.bodybalance.videoplayer.domain.usecase

import com.example.bodybalance.core.domain.models.Video

interface GetAllVideosUseCase {
    suspend operator fun invoke(): Result<List<Video>>
}