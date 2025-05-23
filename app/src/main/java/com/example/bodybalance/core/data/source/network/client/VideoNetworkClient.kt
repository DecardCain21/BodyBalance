package com.example.bodybalance.core.data.source.network.client

import com.example.bodybalance.core.data.dto.VideoDto
import com.example.bodybalance.core.data.source.network.BodyBalanceApiService

class VideoNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    suspend fun getVideo(type: String, category: String): Result<List<VideoDto>> {
        return super.doRequest {
            apiService.getCategoryVideo(type = type, category = category)
        }
    }
}