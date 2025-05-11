package com.example.bodybalance.videoplayer.di

import com.example.bodybalance.videoplayer.domain.usecase.AddPlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.SaveVideoInCacheUseCase
import com.example.bodybalance.videoplayer.domain.usecase.impl.AddPlaylistVideoUseCaseImpl
import com.example.bodybalance.videoplayer.domain.usecase.impl.SaveVideoInCacheUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class VideoPlaylistModuleBinder {

    @Binds
    abstract fun bindSaveVideoInCache(
        saveVideoInCacheImpl: SaveVideoInCacheUseCaseImpl
    ): SaveVideoInCacheUseCase

    @Binds
    abstract fun bindAddPlaylistVideo(
        addPlaylistVideoUseCaseImpl: AddPlaylistVideoUseCaseImpl
    ): AddPlaylistVideoUseCase
}