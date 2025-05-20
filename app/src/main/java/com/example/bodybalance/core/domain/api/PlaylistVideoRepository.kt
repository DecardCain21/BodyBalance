package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow

interface PlaylistVideoRepository {

    fun getAllPlaylistVideos(): Flow<List<Video>>

    suspend fun insertPlaylistVideo(video: Video)

    suspend fun deletePlaylistVideo(video: Video)

    suspend fun deletePlaylistVideoById(id: Double)

    suspend fun getPlaylistVideoById(id: Double): Video?

    suspend fun updatePlaylistVideo(video: Video)

    suspend fun existsPlaylistVideoById(id: Double): Boolean

    suspend fun updateOrderPlaylistVideo(id: Double, order: Int)
}