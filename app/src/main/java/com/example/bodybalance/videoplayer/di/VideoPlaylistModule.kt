package com.example.bodybalance.videoplayer.di

import com.example.bodybalance.videoplayer.domain.usecase.GetAllVideosUseCase
import com.example.bodybalance.videoplayer.domain.usecase.GetVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.SaveVideoInCacheUseCase
import com.example.bodybalance.videoplayer.domain.usecase.impl.GetAllVideosUseCaseImpl
import com.example.bodybalance.videoplayer.domain.usecase.impl.GetVideoUseCaseImpl
import com.example.bodybalance.videoplayer.domain.usecase.impl.SaveVideoInCacheUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class VideoPlaylistModuleBinder {

    @Binds
    abstract fun bindGetVideoUseCase(
        getVideoUseCaseImpl: GetVideoUseCaseImpl
    ): GetVideoUseCase

    @Binds
    abstract fun bindSaveVideoInCache(
        saveVideoInCacheImpl: SaveVideoInCacheUseCaseImpl
    ): SaveVideoInCacheUseCase

    @Binds
    abstract fun bindGetAllVideosUseCaseImpl(
        getAllVideosUseCaseImpl: GetAllVideosUseCaseImpl
    ): GetAllVideosUseCase
}