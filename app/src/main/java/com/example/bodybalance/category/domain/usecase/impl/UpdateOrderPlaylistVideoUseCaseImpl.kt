package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.UpdateOrderPlaylistVideoUseCase
import com.example.bodybalance.core.domain.api.PlaylistVideoRepository
import javax.inject.Inject

class UpdateOrderPlaylistVideoUseCaseImpl @Inject constructor(
    private val playlistVideoRepository: PlaylistVideoRepository
) : UpdateOrderPlaylistVideoUseCase {

    override suspend fun invoke(id: Int, order: Int) {
        playlistVideoRepository.updateOrderPlaylistVideo(id = id, order = order)
    }
}