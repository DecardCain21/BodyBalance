package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToVideo
import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.data.source.network.client.VideoNetworkClient
import com.example.bodybalance.core.domain.api.VideoRepository
import com.example.bodybalance.core.domain.models.Video
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoNetworkClient: VideoNetworkClient,
    private val userAccountLocalSource: UserAccountLocalSource
) : VideoRepository {

    override suspend fun getVideoByCategory(category: String): Result<List<Video>> {
        return videoNetworkClient.getVideo("", category).map { list ->
            list.map { videoDto -> videoDto.convertToVideo() }
        }

    }

    override suspend fun getVideoById(id: Double): Result<Video> {
        TODO("Not yet implemented")
    }
}