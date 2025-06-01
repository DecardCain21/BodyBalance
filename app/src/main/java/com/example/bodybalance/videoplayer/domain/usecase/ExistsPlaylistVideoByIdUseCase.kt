package com.example.bodybalance.videoplayer.domain.usecase

internal interface ExistsPlaylistVideoByIdUseCase {

    suspend operator fun invoke(id: Int): Boolean
}