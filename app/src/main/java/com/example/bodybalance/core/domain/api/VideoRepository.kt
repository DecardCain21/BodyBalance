package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video

interface VideoRepository {

    suspend fun getVideoByCategory(categoryId: Int): Result<List<Video>>

    suspend fun getVideoById(id: Int): Result<Video>
}