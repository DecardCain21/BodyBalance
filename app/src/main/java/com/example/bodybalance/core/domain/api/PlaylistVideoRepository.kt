package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow

interface PlaylistVideoRepository {

    fun getAllPlaylistVideos(): Flow<List<Video>>

    suspend fun insertPlaylistVideo(video: Video)

    suspend fun deletePlaylistVideo(video: Video)

    suspend fun deletePlaylistVideoById(id: Int)

    suspend fun getPlaylistVideoById(id: Int): Video?

    suspend fun updatePlaylistVideo(video: Video)

    suspend fun existsPlaylistVideoById(id: Int): Boolean

    suspend fun updateOrderPlaylistVideo(id: Int, order: Int)
}