package com.example.bodybalance.videoplayer.domain.usecase.impl

import com.example.bodybalance.core.domain.api.PlaylistVideoRepository
import com.example.bodybalance.videoplayer.domain.usecase.ExistsPlaylistVideoByIdUseCase
import javax.inject.Inject

internal class ExistsPlaylistVideoByIdUseCaseImpl @Inject constructor(
    private val playlistVideoRepository: PlaylistVideoRepository
) : ExistsPlaylistVideoByIdUseCase {

    override suspend operator fun invoke(id: Int): Boolean {
        return playlistVideoRepository.existsPlaylistVideoById(id = id)
    }
}