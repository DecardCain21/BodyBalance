package com.example.bodybalance.videoplayer.domain.usecase

public interface ExistsPlaylistVideoByIdUseCase {

    public suspend operator fun invoke(id: Int): Boolean
}