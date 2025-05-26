package com.example.bodybalance.core.data.source.network.client

import com.example.bodybalance.core.data.dto.VideoDto
import com.example.bodybalance.core.data.source.network.BodyBalanceApiService

class VideoNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    suspend fun getVideo(accountTypeId: Int, categoryId: Int): Result<List<VideoDto>> {
        return super.doRequest {
            apiService.getCategoryVideo(type = accountTypeId, category = categoryId)
        }
    }

    suspend fun getVideoById(videoId:Int): Result<VideoDto> {
        return super.doRequest{
            apiService.getVideoById(videoId = videoId)
        }
    }
}