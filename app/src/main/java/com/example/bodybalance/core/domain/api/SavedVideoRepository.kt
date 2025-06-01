package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video

public interface SavedVideoRepository {

    public suspend fun getAllSavedVideos(): List<Video>

    public suspend fun insertVideo(video: Video)
}