package com.example.bodybalance.category.domain.usecase

import com.example.bodybalance.core.domain.models.Video
import kotlinx.coroutines.flow.Flow

internal interface GetAllSavedVideoUseCase {

    operator fun invoke(): Flow<List<Video>>
}