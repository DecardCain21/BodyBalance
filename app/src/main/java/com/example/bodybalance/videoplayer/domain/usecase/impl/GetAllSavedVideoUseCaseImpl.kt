package com.example.bodybalance.videoplayer.domain.usecase.impl

import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.videoplayer.domain.usecase.GetAllSavedVideoUseCase
import javax.inject.Inject

public class GetAllSavedVideoUseCaseImpl @Inject constructor(
    private val savedVideoRepository: SavedVideoRepository
) : GetAllSavedVideoUseCase {

    override suspend fun invoke(): List<Video> {
        return savedVideoRepository.getAllSavedVideo()
    }
}