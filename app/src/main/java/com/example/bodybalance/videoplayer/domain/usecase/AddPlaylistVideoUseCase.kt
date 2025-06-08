package com.example.bodybalance.videoplayer.domain.usecase

import com.example.bodybalance.core.domain.models.Video

public interface AddPlaylistVideoUseCase {

    public suspend operator fun invoke(video: Video)
}