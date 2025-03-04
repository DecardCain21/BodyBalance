package com.example.bodybalance.core.data.network.client

import com.example.bodybalance.core.data.dto.CategoryResponse
import com.example.bodybalance.core.data.network.BodyBalanceApiService

class VideoNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    suspend fun getVideo(category: String): Result<CategoryResponse> {
        return super.doRequest {
            apiService.getCategoryVideo(type = "", category = category)
        }
    }
}