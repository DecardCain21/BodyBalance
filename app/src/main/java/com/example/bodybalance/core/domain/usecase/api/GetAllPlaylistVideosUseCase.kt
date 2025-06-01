package com.example.bodybalance.core.domain.usecase.api

import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow

public interface GetAllPlaylistVideosUseCase {

    public suspend operator fun invoke(): Flow<List<Video>>
}