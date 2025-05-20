package com.example.bodybalance.core.data.source.local.database.api

import com.example.bodybalance.core.data.source.local.database.entity.PlaylistVideoEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistVideoLocalSource {

    fun getAllPlaylistVideos(): Flow<List<PlaylistVideoEntity>>

    suspend fun insertPlaylistVideo(entity: PlaylistVideoEntity)

    suspend fun deletePlaylistVideo(entity: PlaylistVideoEntity)

    suspend fun deletePlaylistVideoById(id: Double)

    suspend fun getPlaylistVideoById(id: Double): PlaylistVideoEntity?

    suspend fun updatePlaylistVideo(entity: PlaylistVideoEntity)

    suspend fun existsPlaylistVideoById(id: Double): Boolean
}
