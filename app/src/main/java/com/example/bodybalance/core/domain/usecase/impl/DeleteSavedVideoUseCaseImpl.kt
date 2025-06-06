package com.example.bodybalance.core.domain.usecase.impl

import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.DeleteSavedVideoUseCase
import javax.inject.Inject

public class DeleteSavedVideoUseCaseImpl @Inject constructor(
    private val savedVideoRepository: SavedVideoRepository
) : DeleteSavedVideoUseCase {

    override suspend fun invoke(video: Video) {
        savedVideoRepository.deleteSavedVideo(video = video)
    }
}