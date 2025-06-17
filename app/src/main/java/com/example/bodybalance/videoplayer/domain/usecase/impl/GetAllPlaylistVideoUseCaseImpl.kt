package com.example.bodybalance.videoplayer.domain.usecase.impl

import com.example.bodybalance.core.domain.api.PlaylistVideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.videoplayer.domain.usecase.GetAllPlaylistVideoUseCase
import javax.inject.Inject

internal class GetAllPlaylistVideoUseCaseImpl @Inject constructor(
    private val playlistVideoRepository: PlaylistVideoRepository
): GetAllPlaylistVideoUseCase {

    override suspend fun invoke(): List<Video> {
        return playlistVideoRepository.getAllPlaylistVideo()
    }
}