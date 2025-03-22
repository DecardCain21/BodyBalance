package com.example.bodybalance.videoplayer.domain.usecase.impl

import com.example.bodybalance.core.data.convertor.convertToSavedVideo
import com.example.bodybalance.core.data.source.local.database.dao.VideoCacheDao
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.videoplayer.domain.usecase.SaveVideoInCacheUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SaveVideoInCacheUseCaseImpl @Inject constructor(
    private val savedVideoDao: VideoCacheDao
) : SaveVideoInCacheUseCase {

    override suspend operator fun invoke(video: Video) {
        withContext(Dispatchers.IO) {
            savedVideoDao.insert(video.convertToSavedVideo())
        }
    }
}