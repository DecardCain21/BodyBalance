package com.example.bodybalance.core.data.source.network

import com.example.bodybalance.core.data.dto.AccountDto
import com.example.bodybalance.core.data.dto.CategoryDto
import com.example.bodybalance.core.data.dto.VideoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BodyBalanceApiService {

    @GET("v1/video_categories")
    suspend fun getCategoryVideo(
        @Query("type") type: Int,
        @Query("category") category: Int,
    ): List<VideoDto>

    @GET("v1/category")
    suspend fun getCategory(
        @Query("type") type: Int,
    ): List<CategoryDto>

    @GET("v1/login")
    suspend fun checkAccount(
        @Query("username") username: String
    ): AccountDto
}