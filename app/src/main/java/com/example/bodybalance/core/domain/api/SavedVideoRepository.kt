package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video

interface SavedVideoRepository {
    suspend fun getAllSavedVideos(): List<Video>
    suspend fun insertVideo(video: Video)
}