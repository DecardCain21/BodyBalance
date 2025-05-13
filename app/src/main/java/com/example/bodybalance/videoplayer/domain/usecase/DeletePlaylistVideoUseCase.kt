package com.example.bodybalance.videoplayer.domain.usecase

import com.example.bodybalance.core.domain.models.Video

interface DeletePlaylistVideoUseCase {

    suspend operator fun invoke(video: Video)
}