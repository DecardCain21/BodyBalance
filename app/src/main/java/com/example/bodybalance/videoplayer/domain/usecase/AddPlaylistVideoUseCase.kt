package com.example.bodybalance.videoplayer.domain.usecase

import com.example.bodybalance.core.domain.models.Video

interface AddPlaylistVideoUseCase {

    suspend operator fun invoke(video: Video)
}