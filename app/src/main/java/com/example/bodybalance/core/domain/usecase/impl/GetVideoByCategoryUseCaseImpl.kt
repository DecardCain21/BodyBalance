package com.example.bodybalance.core.domain.usecase.impl

import com.example.bodybalance.core.domain.api.VideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.GetVideoByCategoryUseCase
import javax.inject.Inject

class GetVideoByCategoryUseCaseImpl @Inject constructor(
    private val videoRepository: VideoRepository
) : GetVideoByCategoryUseCase {

    override suspend fun invoke(categoryId: Int): Result<List<Video>> =
        videoRepository.getVideoByCategory(categoryId)
}