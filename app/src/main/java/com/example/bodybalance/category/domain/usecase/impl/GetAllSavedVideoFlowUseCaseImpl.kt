package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.GetAllSavedVideoFlowUseCase
import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public class GetAllSavedVideoFlowUseCaseImpl @Inject constructor(
    private val savedVideoRepository: SavedVideoRepository
) : GetAllSavedVideoFlowUseCase {

    override fun invoke(): Flow<List<Video>> {
        return savedVideoRepository.getAllSavedVideoFlow()
    }
}