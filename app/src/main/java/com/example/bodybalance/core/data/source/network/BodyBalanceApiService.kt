package com.example.bodybalance.core.data.source.network

import com.example.bodybalance.core.data.dto.VideoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BodyBalanceApiService {

    @GET("v1/video_categories")
    suspend fun getCategoryVideo(
        @Query("type") type: String,
        @Query("category") category: String,
    ): List<VideoDto>

    @GET("v1/category")
    suspend fun getCategory(): List<String>

    @GET("v1/login")
    suspend fun checkAccount(
        @Query("username") username: String
    ): Boolean
}