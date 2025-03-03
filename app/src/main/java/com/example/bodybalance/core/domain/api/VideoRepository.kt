package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Category

interface VideoRepository {
    suspend fun getVideo(
        category: String
        //account: Account, category: Int, prevId: Int
    ): Result<Category>
}