package com.example.bodybalance.core.domain.usecase.impl

import com.example.bodybalance.core.domain.api.VideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.GetVideoByIdUseCase
import javax.inject.Inject

class GetVideoByIdUseCaseImpl @Inject constructor(
    private val videoRepository: VideoRepository
) : GetVideoByIdUseCase {
    override suspend fun invoke(videoId: Int): Result<Video> =
        videoRepository.getVideoById(videoId)

}