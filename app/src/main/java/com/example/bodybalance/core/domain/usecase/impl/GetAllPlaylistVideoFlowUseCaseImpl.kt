package com.example.bodybalance.core.domain.usecase.impl

import com.example.bodybalance.core.domain.usecase.api.GetAllPlaylistVideoFlowUseCase
import com.example.bodybalance.core.domain.api.PlaylistVideoRepository
import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public class GetAllPlaylistVideoFlowUseCaseImpl @Inject constructor(
    private val repository: PlaylistVideoRepository
) : GetAllPlaylistVideoFlowUseCase {

    override suspend operator fun invoke(): Flow<List<Video>> {
        return repository.getAllPlaylistVideoFlow()
    }
}