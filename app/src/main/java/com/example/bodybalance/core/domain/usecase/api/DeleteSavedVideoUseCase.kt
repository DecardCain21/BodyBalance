package com.example.bodybalance.core.domain.usecase.api

import com.example.bodybalance.core.domain.models.Video

public interface DeleteSavedVideoUseCase {

    public suspend operator fun invoke(video: Video)
}