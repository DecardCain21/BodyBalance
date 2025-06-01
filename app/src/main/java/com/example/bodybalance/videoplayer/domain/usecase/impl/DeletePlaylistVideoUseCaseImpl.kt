package com.example.bodybalance.videoplayer.domain.usecase.impl

import com.example.bodybalance.core.domain.api.PlaylistVideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.videoplayer.domain.usecase.DeletePlaylistVideoUseCase
import javax.inject.Inject

internal class DeletePlaylistVideoUseCaseImpl @Inject constructor(
    private val playlistVideoRepository: PlaylistVideoRepository
) : DeletePlaylistVideoUseCase {

    override suspend fun invoke(video: Video) {
        playlistVideoRepository.deletePlaylistVideo(video)
    }
}