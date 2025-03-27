package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToCategory
import com.example.bodybalance.core.data.source.local.storage.PreferencesStorage
import com.example.bodybalance.core.data.source.network.client.VideoNetworkClient
import com.example.bodybalance.core.domain.api.VideoRepository
import com.example.bodybalance.core.domain.models.Category
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoNetworkClient: VideoNetworkClient,
    private val preferencesStorage: PreferencesStorage
) : VideoRepository {

    override suspend fun getVideo(category: String): Result<Category> {
        return videoNetworkClient.getVideo(type = preferencesStorage.login, category = category)
            .map { it.convertToCategory() }
    }
}