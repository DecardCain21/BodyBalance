package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToCategory
import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.domain.models.Category
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.data.source.network.client.VideoNetworkClient
import com.example.bodybalance.core.domain.api.VideoRepository
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoNetworkClient: VideoNetworkClient,
    private val userAccountLocalSource: UserAccountLocalSource
) : VideoRepository {

    override suspend fun getVideoByCategory(category: String): Result<Category> {
        return videoNetworkClient.getVideo(
            type = userAccountLocalSource.getActiveAccount()?.name ?: "",
            category = category
        ).map { it.convertToCategory() }
    }

    override suspend fun getVideoById(id: Double): Result<Video> {
        TODO("Not yet implemented")
    }
}