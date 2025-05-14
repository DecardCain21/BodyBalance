package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video

interface PlaylistVideoRepository {

    suspend fun insertPlaylistVideo(video: Video)

    suspend fun deletePlaylistVideo(video: Video)

    suspend fun deletePlaylistVideoById(id: Double)

    suspend fun getPlaylistVideoById(id: Double): Double

    suspend fun getPlaylistAllVideos(): List<Double>

    suspend fun updatePlaylistVideo(video: Video)

    suspend fun existsPlaylistVideoById(id: Double): Boolean
}