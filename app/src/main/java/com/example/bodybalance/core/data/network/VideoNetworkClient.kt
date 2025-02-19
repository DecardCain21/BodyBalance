package com.example.bodybalance.core.data.network

import com.example.bodybalance.core.data.dto.VideoResponse

class VideoNetworkClient(private val apiService: BodyBalanceApiService) : RetrofitNetworkClient() {

    suspend fun doRequest(): Result<VideoResponse> {
        val result = super.doRequest { apiService.doRequest() }
        return result
    }
}