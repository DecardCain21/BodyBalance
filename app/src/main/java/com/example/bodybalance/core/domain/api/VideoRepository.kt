package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video

interface VideoRepository {

    suspend fun getVideoByCategory(category: String): Result<List<Video>>

    suspend fun getVideoById(id: Double): Result<Video>
}