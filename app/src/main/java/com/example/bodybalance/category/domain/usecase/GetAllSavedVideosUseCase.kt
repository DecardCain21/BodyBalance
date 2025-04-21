package com.example.bodybalance.category.domain.usecase

import com.example.bodybalance.core.domain.models.Video

interface GetAllSavedVideosUseCase {
    suspend operator fun invoke(): Result<List<Video>>
}