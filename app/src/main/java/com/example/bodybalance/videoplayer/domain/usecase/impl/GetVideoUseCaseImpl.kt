package com.example.bodybalance.videoplayer.domain.usecase.impl

import com.example.bodybalance.core.domain.api.VideoRepository
import com.example.bodybalance.core.domain.models.Category
import com.example.bodybalance.videoplayer.domain.usecase.GetVideoUseCase
import javax.inject.Inject

class GetVideoUseCaseImpl @Inject constructor(
    private val videoRepository: VideoRepository
) : GetVideoUseCase {

    override suspend fun invoke(category: String): Result<Category> =
        videoRepository.getVideo(category)
}