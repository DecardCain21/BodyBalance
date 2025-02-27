package com.example.bodybalance.videoplayer.domain.usecase

import com.example.bodybalance.core.data.dto.VideoResponse

interface GetVideoUseCase {

    suspend operator fun invoke(category: String): Result<VideoResponse>
}