package com.example.bodybalance.core.domain.usecase.impl

import com.example.bodybalance.core.domain.api.VideoRepository
import com.example.bodybalance.core.domain.models.Category
import com.example.bodybalance.core.domain.usecase.api.GetVideoByCategoryUseCase
import javax.inject.Inject

class GetVideoByCategoryByCategoryUseCaseImpl @Inject constructor(
    private val videoRepository: VideoRepository
) : GetVideoByCategoryUseCase {

    override suspend fun invoke(category: String): Result<Category> =
        videoRepository.getVideoByCategory(category)
}