package com.example.bodybalance.core.data.network

import com.example.bodybalance.core.data.dto.CategoryResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BodyBalanceApiService {

    @GET("/video")
    suspend fun getCategoryVideo(
        @Query("type") type: String,
        @Query("category") category: String,
    ): CategoryResponse

    @GET("/category")
    suspend fun getCategory(): List<String>

    @GET("/login")
    suspend fun checkAccount(
        @Query("type") type: String
    ): Boolean
}