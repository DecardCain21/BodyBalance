package com.example.bodybalance.videoplayer.domain.usecase

import com.example.bodybalance.core.domain.models.Video

interface SaveVideoInCacheUseCase {

    suspend operator fun invoke(video: Video)
}