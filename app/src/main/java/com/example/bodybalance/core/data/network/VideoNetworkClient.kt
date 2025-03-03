package com.example.bodybalance.core.data.network

import com.example.bodybalance.core.data.dto.CategoryResponse

class VideoNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    suspend fun getVideo(category: String): Result<CategoryResponse> {
        return super.doRequest {
            apiService.doRequest(type = "", category = category)
        }
    }
}