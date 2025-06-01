package com.example.bodybalance.videoplayer.domain.usecase.impl

import com.example.bodybalance.core.domain.api.PlaylistVideoRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.videoplayer.domain.usecase.AddPlaylistVideoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AddPlaylistVideoUseCaseImpl @Inject constructor(
    private val playlistVideoRepository: PlaylistVideoRepository
) : AddPlaylistVideoUseCase {
    override suspend fun invoke(video: Video) {
        withContext(Dispatchers.IO) {
            playlistVideoRepository.insertPlaylistVideo(video = video)
        }
    }
}