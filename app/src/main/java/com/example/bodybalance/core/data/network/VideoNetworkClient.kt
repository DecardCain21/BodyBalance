package com.example.bodybalance.core.data.network

class VideoNetworkClient(private val apiService: BodyBalanceApiService) : RetrofitNetworkClient() {

    suspend fun doRequest() {
        super.doRequest {
            apiService.doRequest()
        }
    }
}