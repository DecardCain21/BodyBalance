package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertEntity
import com.example.bodybalance.core.data.convertor.convertToVideo
import com.example.bodybalance.core.data.source.local.database.api.PlaylistVideoLocalSource
import com.example.bodybalance.core.domain.api.PlaylistVideoRepository
import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlaylistVideoRepositoryImpl @Inject constructor(
    private val playlistVideoLocalSource: PlaylistVideoLocalSource
) : PlaylistVideoRepository {

    override fun getAllPlaylistVideos(): Flow<List<Video>> {
        return playlistVideoLocalSource.getAllPlaylistVideos().map { list ->
            list.map { entity -> entity.convertToVideo() }
        }
    }

    override suspend fun insertPlaylistVideo(video: Video) {
        playlistVideoLocalSource.insertPlaylistVideo(entity = video.convertEntity())
    }

    override suspend fun deletePlaylistVideo(video: Video) {
        playlistVideoLocalSource.deletePlaylistVideo(entity = video.convertEntity())
    }

    override suspend fun deletePlaylistVideoById(id: Int) {
        playlistVideoLocalSource.deletePlaylistVideoById(id = id)
    }

    override suspend fun getPlaylistVideoById(id: Int): Video? {
        return playlistVideoLocalSource.getPlaylistVideoById(id = id)?.convertToVideo()
    }

    override suspend fun updatePlaylistVideo(video: Video) {
        playlistVideoLocalSource.updatePlaylistVideo(entity = video.convertEntity())
    }

    override suspend fun existsPlaylistVideoById(id: Int): Boolean {
        return playlistVideoLocalSource.existsPlaylistVideoById(id = id)
    }

    override suspend fun updateOrderPlaylistVideo(id: Int, order: Int) {
        playlistVideoLocalSource.updateOrderPlaylistVideo(id = id, order = order)
    }
}