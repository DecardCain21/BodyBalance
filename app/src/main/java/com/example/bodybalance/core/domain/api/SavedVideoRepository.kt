package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow

public interface SavedVideoRepository {

    public fun getAllSavedVideoFlow(): Flow<List<Video>>

    public suspend fun getAllSavedVideo(): List<Video>

    public suspend fun insertVideo(video: Video)

    public suspend fun deleteSavedVideo(video: Video)
}