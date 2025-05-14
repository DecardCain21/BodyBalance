package com.example.bodybalance.videoplayer.domain.usecase

interface ExistsPlaylistVideoByIdUseCase {

    suspend operator fun invoke(id: Double): Boolean
}