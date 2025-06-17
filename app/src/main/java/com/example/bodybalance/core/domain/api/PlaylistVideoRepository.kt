package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow

public interface PlaylistVideoRepository {

    public fun getAllPlaylistVideoFlow(): Flow<List<Video>>

    public suspend fun getAllPlaylistVideo(): List<Video>

    public suspend fun insertPlaylistVideo(video: Video)

    public suspend fun deletePlaylistVideo(video: Video)

    public suspend fun deletePlaylistVideoById(id: Int)

    public suspend fun getPlaylistVideoById(id: Int): Video?

    public suspend fun updatePlaylistVideo(video: Video)

    public suspend fun existsPlaylistVideoById(id: Int): Boolean

    public suspend fun updateOrderPlaylistVideo(id: Int, order: Int)
}