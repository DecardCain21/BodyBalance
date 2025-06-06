package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.GetAllSavedVideoUseCase
import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class GetAllSavedVideoUseCaseImpl @Inject constructor(
    private val savedVideoRepository: SavedVideoRepository
) : GetAllSavedVideoUseCase {

    override fun invoke(): Flow<List<Video>> {
        return savedVideoRepository.getAllSavedVideo()
    }
}