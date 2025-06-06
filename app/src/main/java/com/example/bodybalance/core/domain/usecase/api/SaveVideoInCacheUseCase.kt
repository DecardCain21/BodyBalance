package com.example.bodybalance.core.domain.usecase.api

import com.example.bodybalance.core.domain.models.Video

public interface SaveVideoInCacheUseCase {

    public suspend operator fun invoke(video: Video)
}