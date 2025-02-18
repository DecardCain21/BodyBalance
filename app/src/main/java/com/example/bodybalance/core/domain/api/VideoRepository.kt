package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.data.dto.VideoResponse
import com.example.bodybalance.core.domain.models.Account

interface VideoRepository {
    suspend fun getVideo(account: Account, category: Int, prevId: Int): Result<VideoResponse>
}