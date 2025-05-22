package com.example.bodybalance.core.data.source.network.client

import com.example.bodybalance.core.data.dto.CategoryResponse
import com.example.bodybalance.core.data.dto.Test
import com.example.bodybalance.core.data.source.network.BodyBalanceApiService

class VideoNetworkClient(
    private val apiService: BodyBalanceApiService
) : RetrofitNetworkClient() {

    suspend fun getVideo(type: String, category: String): Result<List<Test>> {
        return super.doRequest {
            apiService.getCategoryVideo(type = "basic", category = "Шея")
        }
    }
}