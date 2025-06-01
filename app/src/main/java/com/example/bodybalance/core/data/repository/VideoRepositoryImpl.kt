package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToVideo
import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.data.source.network.client.VideoNetworkClient
import com.example.bodybalance.core.domain.api.VideoRepository
import com.example.bodybalance.core.domain.models.Video
import javax.inject.Inject

internal class VideoRepositoryImpl @Inject constructor(
    private val videoNetworkClient: VideoNetworkClient,
    private val userAccountLocalSource: UserAccountLocalSource
) : VideoRepository {

    override suspend fun getVideoByCategory(categoryId: Int): Result<List<Video>> {
        val accountType =
            userAccountLocalSource.getActiveAccount()?.id ?: return Result.failure(Exception())
        return videoNetworkClient.getVideo(accountType, categoryId).map { list ->
            list.map { videoDto -> videoDto.convertToVideo() }
        }
    }

    override suspend fun getVideoById(id: Int): Result<Video> {
        return videoNetworkClient.getVideoById(id).map { videoDto -> videoDto.convertToVideo() }
    }
}