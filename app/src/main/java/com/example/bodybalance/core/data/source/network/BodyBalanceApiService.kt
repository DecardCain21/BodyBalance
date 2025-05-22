package com.example.bodybalance.core.data.source.network

import com.example.bodybalance.core.data.dto.CategoryResponse
import com.example.bodybalance.core.data.dto.Test
import retrofit2.http.GET
import retrofit2.http.Query

interface BodyBalanceApiService {

    @GET("v1/video")
    suspend fun getCategoryVideo(
        @Query("type") type: String,
        @Query("category") category: String,
    ): List<Test>

    @GET("/category")
    suspend fun getCategory(): List<String>

    @GET("/login")
    suspend fun checkAccount(
        @Query("type") type: String
    ): Boolean
}