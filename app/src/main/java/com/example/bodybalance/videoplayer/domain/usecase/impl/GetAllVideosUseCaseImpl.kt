package com.example.bodybalance.videoplayer.domain.usecase.impl

import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.videoplayer.domain.usecase.GetAllVideosUseCase
import javax.inject.Inject

class GetAllVideosUseCaseImpl @Inject constructor(private val repository: SavedVideoRepository) :
    GetAllVideosUseCase {
    override suspend operator fun invoke(): Result<List<Video>> {
        return try {
            val videos = repository.getAllVideos()
            Result.success(videos)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}