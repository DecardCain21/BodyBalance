package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToSavedVideo
import com.example.bodybalance.core.data.convertor.convertToVideo
import com.example.bodybalance.core.data.source.local.database.dao.VideoCacheDao
import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavedVideoRepositoryImpl @Inject constructor(private val savedVideoDao: VideoCacheDao) : SavedVideoRepository {
    override suspend fun getAllVideos() = withContext(Dispatchers.IO) {
        savedVideoDao.getAll().map { it.convertToVideo() }
    }

    override suspend fun insertVideo(video: Video) {
        savedVideoDao.insert(video.convertToSavedVideo())
    }
}