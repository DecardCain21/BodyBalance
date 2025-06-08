package com.example.bodybalance.videoplayer.domain.usecase

import com.example.bodybalance.core.domain.models.Video

public interface GetAllSavedVideoUseCase {

    public suspend operator fun invoke(): List<Video>
}