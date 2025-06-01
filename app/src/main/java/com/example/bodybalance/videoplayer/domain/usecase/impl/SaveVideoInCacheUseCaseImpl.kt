package com.example.bodybalance.videoplayer.domain.usecase.impl

import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.videoplayer.domain.usecase.SaveVideoInCacheUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


internal class SaveVideoInCacheUseCaseImpl @Inject constructor(
    private val savedVideoRepository: SavedVideoRepository
) : SaveVideoInCacheUseCase {

    override suspend operator fun invoke(video: Video) {
        withContext(Dispatchers.IO) {
            savedVideoRepository.insertVideo(video)
        }
    }
}