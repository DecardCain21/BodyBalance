package com.example.bodybalance.core.domain.usecase.api

import com.example.bodybalance.core.domain.models.Video

public interface GetVideoByCategoryUseCase {

    public suspend operator fun invoke(categoryId: Int): Result<List<Video>>
}