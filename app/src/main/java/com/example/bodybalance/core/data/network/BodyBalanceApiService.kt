package com.example.bodybalance.core.data.network

import com.example.bodybalance.core.data.dto.VideoResponse
import retrofit2.http.GET

interface BodyBalanceApiService {
    @GET("request/textEscapes.json")
    suspend fun doRequest(): VideoResponse
}