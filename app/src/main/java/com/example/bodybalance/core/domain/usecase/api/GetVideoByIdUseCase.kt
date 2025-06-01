package com.example.bodybalance.core.domain.usecase.api

import com.example.bodybalance.core.domain.models.Video

public interface GetVideoByIdUseCase {

    public suspend operator fun invoke(videoId: Int): Result<Video>
}