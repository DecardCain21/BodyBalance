package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Category
import com.example.bodybalance.core.domain.models.Video

interface VideoRepository {

    suspend fun getVideoByCategory(category: String): Result<Category>

    suspend fun getVideoById(id: Double): Result<Video>
}