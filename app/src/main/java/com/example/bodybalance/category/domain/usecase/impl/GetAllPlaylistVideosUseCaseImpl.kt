package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.GetAllPlaylistVideosUseCase
import com.example.bodybalance.core.domain.api.PlaylistVideoRepository
import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPlaylistVideosUseCaseImpl @Inject constructor(
    private val repository: PlaylistVideoRepository
) : GetAllPlaylistVideosUseCase {

    override suspend operator fun invoke(): Flow<List<Video>> {
        return repository.getAllPlaylistVideos()
    }
}