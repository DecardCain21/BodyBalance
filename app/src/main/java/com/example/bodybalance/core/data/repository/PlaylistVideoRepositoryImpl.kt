package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToPlaylistVideo
import com.example.bodybalance.core.data.source.local.database.dao.PlaylistVideoDao
import com.example.bodybalance.core.domain.api.PlaylistVideoRepository
import com.example.bodybalance.core.domain.models.Video

class PlaylistVideoRepositoryImpl(
    private val playlistVideoDao: PlaylistVideoDao
) : PlaylistVideoRepository {

    override suspend fun insertPlaylistVideo(video: Video) {
        playlistVideoDao.insertPlaylistVideo(video.convertToPlaylistVideo())
    }

    override suspend fun deletePlaylistVideo(video: Video) {
        playlistVideoDao.deletePlaylistVideo(video.convertToPlaylistVideo())
    }

    override suspend fun deletePlaylistVideoById(id: Double) {
        playlistVideoDao.deletePlaylistVideoById(id)
    }

    override suspend fun getPlaylistVideoById(id: Double): Double {
       return playlistVideoDao.getPlaylistVideoById(id)?.id ?: 0.0
    }

    override suspend fun getPlaylistAllVideos(): List<Double> {
        return playlistVideoDao.getPlaylistAllVideos().map { it.id }
    }

    override suspend fun updatePlaylistVideo(video: Video) {
        playlistVideoDao.updatePlaylistVideo(video.convertToPlaylistVideo())
    }
}