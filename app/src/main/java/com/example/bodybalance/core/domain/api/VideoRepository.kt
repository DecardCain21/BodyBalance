package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Video

public interface VideoRepository {

    public suspend fun getVideoByCategory(categoryId: Int): Result<List<Video>>

    public suspend fun getVideoById(id: Int): Result<Video>
}