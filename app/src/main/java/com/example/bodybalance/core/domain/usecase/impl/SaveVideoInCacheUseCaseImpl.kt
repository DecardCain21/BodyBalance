package com.example.bodybalance.core.domain.usecase.impl

import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.SaveVideoInCacheUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

public class SaveVideoInCacheUseCaseImpl @Inject constructor(
    private val savedVideoRepository: SavedVideoRepository
) : SaveVideoInCacheUseCase {

    override suspend operator fun invoke(video: Video) {
        withContext(Dispatchers.IO) {
            savedVideoRepository.insertVideo(video)
        }
    }
}