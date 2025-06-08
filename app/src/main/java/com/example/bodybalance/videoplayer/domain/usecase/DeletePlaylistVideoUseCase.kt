package com.example.bodybalance.videoplayer.domain.usecase

import com.example.bodybalance.core.domain.models.Video

public interface DeletePlaylistVideoUseCase {

    public suspend operator fun invoke(video: Video)
}