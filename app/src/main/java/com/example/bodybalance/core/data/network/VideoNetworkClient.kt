package com.example.bodybalance.core.data.network

import com.example.bodybalance.core.data.dto.VideoResponse

class VideoNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    suspend fun getVideo(): Result<VideoResponse> {
        return super.doRequest { apiService.doRequest(type = "", category = "Шея") }
    }
}