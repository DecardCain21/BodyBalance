package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertPlaylistEntity
import com.example.bodybalance.core.data.convertor.convertToVideo
import com.example.bodybalance.core.data.source.local.database.api.PlaylistVideoLocalSource
import com.example.bodybalance.core.domain.api.PlaylistVideoRepository
import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class PlaylistVideoRepositoryImpl @Inject constructor(
    private val playlistVideoLocalSource: PlaylistVideoLocalSource
) : PlaylistVideoRepository {

    override fun getAllPlaylistVideoFlow(): Flow<List<Video>> {
        return playlistVideoLocalSource.getAllPlaylistVideoFlow().map { list ->
            list.map { entity -> entity.convertToVideo() }
        }
    }

    override suspend fun getAllPlaylistVideo(): List<Video> {
        return playlistVideoLocalSource.getAllPlaylistVideo().map { it.convertToVideo() }
    }

    override suspend fun insertPlaylistVideo(video: Video) {
        playlistVideoLocalSource.insertPlaylistVideo(entity = video.convertPlaylistEntity())
    }

    override suspend fun deletePlaylistVideo(video: Video) {
        playlistVideoLocalSource.deletePlaylistVideo(entity = video.convertPlaylistEntity())
    }

    override suspend fun deletePlaylistVideoById(id: Int) {
        playlistVideoLocalSource.deletePlaylistVideoById(id = id)
    }

    override suspend fun getPlaylistVideoById(id: Int): Video? {
        return playlistVideoLocalSource.getPlaylistVideoById(id = id)?.convertToVideo()
    }

    override suspend fun updatePlaylistVideo(video: Video) {
        playlistVideoLocalSource.updatePlaylistVideo(entity = video.convertPlaylistEntity())
    }

    override suspend fun existsPlaylistVideoById(id: Int): Boolean {
        return playlistVideoLocalSource.existsPlaylistVideoById(id = id)
    }

    override suspend fun updateOrderPlaylistVideo(id: Int, order: Int) {
        playlistVideoLocalSource.updateOrderPlaylistVideo(id = id, order = order)
    }
}