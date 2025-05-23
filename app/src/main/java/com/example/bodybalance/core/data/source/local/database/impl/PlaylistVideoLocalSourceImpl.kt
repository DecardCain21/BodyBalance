package com.example.bodybalance.core.data.source.local.database.impl

import com.example.bodybalance.core.data.source.local.database.api.PlaylistVideoLocalSource
import com.example.bodybalance.core.data.source.local.database.dao.PlaylistVideoDao
import com.example.bodybalance.core.data.source.local.database.entity.PlaylistVideoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistVideoLocalSourceImpl @Inject constructor(
    private val dao: PlaylistVideoDao
) : PlaylistVideoLocalSource {

    override fun getAllPlaylistVideos(): Flow<List<PlaylistVideoEntity>> {
        return dao.getAllPlaylistVideos()
    }

    override suspend fun insertPlaylistVideo(entity: PlaylistVideoEntity) {
        dao.insertPlaylistVideo(entity)
    }

    override suspend fun deletePlaylistVideo(entity: PlaylistVideoEntity) {
        dao.deletePlaylistVideo(entity)
    }

    override suspend fun deletePlaylistVideoById(id: Int) {
        dao.deletePlaylistVideoById(id)
    }

    override suspend fun getPlaylistVideoById(id: Int): PlaylistVideoEntity? {
        return dao.getPlaylistVideoById(id)
    }

    override suspend fun updatePlaylistVideo(entity: PlaylistVideoEntity) {
        dao.updatePlaylistVideo(entity)
    }

    override suspend fun existsPlaylistVideoById(id: Int): Boolean {
        return dao.existsById(id)
    }

    override suspend fun updateOrderPlaylistVideo(id: Int, order: Int) {
        dao.updateOrder(id = id, order = order)
    }
}