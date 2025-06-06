package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow

public interface SavedVideoRepository {

    public fun getAllSavedVideo(): Flow<List<Video>>

    public suspend fun insertVideo(video: Video)
}