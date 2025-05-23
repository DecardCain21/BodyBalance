package com.example.bodybalance.core.domain.usecase.api

import com.example.bodybalance.core.domain.models.Video

interface GetVideoByCategoryUseCase {

    suspend operator fun invoke(category: String): Result<List<Video>>
}