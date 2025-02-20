package com.example.bodybalance.core.data.network

import com.example.bodybalance.core.data.dto.VideoResponse

class VideoNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    suspend fun execute(): Result<VideoResponse> {
        return super.doRequest { apiService.doRequest() }
    }
}