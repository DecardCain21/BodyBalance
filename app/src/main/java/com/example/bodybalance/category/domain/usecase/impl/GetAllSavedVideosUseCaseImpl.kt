package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.category.domain.usecase.GetAllSavedVideosUseCase
import javax.inject.Inject

class GetAllSavedVideosUseCaseImpl @Inject constructor(
    private val repository: SavedVideoRepository
) : GetAllSavedVideosUseCase {
    override suspend operator fun invoke(): Result<List<Video>> {
        return try {
            val videos = repository.getAllSavedVideos()
            Result.success(videos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}