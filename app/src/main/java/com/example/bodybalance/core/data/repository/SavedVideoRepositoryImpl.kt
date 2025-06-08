package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToSavedVideo
import com.example.bodybalance.core.data.convertor.convertToVideo
import com.example.bodybalance.core.data.source.local.database.dao.VideoCacheDao
import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SavedVideoRepositoryImpl @Inject constructor(
    private val savedVideoDao: VideoCacheDao
) : SavedVideoRepository {

    override fun getAllSavedVideoFlow(): Flow<List<Video>> {
        return savedVideoDao.getAllFlow().map { list ->
            list.map { video ->
                video.convertToVideo()
            }
        }
    }

    override suspend fun getAllSavedVideo(): List<Video> {
        return savedVideoDao.getAll().map { it.convertToVideo() }
    }

    override suspend fun insertVideo(video: Video) {
        savedVideoDao.insert(video.convertToSavedVideo())
    }

    override suspend fun deleteSavedVideo(video: Video) {
        savedVideoDao.delete(video.convertToSavedVideo())
    }

    override suspend fun deleteAllSavedVideo() {
        savedVideoDao.deleteAllSavedVideo()
    }

    override suspend fun updateOrderSavedVideo(id: Int, order: Int) {
        savedVideoDao.updateOrder(id = id, order = order)
    }
}