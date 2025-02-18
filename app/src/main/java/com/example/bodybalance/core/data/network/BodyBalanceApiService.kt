package com.example.bodybalance.core.data.network

import com.example.bodybalance.core.data.dto.VideoResponse
import retrofit2.http.GET

interface BodyBalanceApiService {
    @GET("/")
    suspend fun doRequest(): VideoResponse
}