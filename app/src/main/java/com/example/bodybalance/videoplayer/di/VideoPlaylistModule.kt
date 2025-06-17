package com.example.bodybalance.videoplayer.di

import com.example.bodybalance.videoplayer.domain.usecase.AddPlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.DeletePlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.ExistsPlaylistVideoByIdUseCase
import com.example.bodybalance.core.domain.usecase.api.SaveVideoInCacheUseCase
import com.example.bodybalance.videoplayer.domain.usecase.impl.AddPlaylistVideoUseCaseImpl
import com.example.bodybalance.videoplayer.domain.usecase.impl.DeletePlaylistVideoUseCaseImpl
import com.example.bodybalance.videoplayer.domain.usecase.impl.ExistsPlaylistVideoByIdUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.SaveVideoInCacheUseCaseImpl
import com.example.bodybalance.videoplayer.domain.usecase.GetAllPlaylistVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.GetAllSavedVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.impl.GetAllPlaylistVideoUseCaseImpl
import com.example.bodybalance.videoplayer.domain.usecase.impl.GetAllSavedVideoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class VideoPlaylistModuleBinder {

    @Binds
    abstract fun bindAddPlaylistVideo(
        addPlaylistVideoUseCaseImpl: AddPlaylistVideoUseCaseImpl
    ): AddPlaylistVideoUseCase

    @Binds
    abstract fun bindDeletePlaylistVideo(
        deletePlaylistVideoUseCaseImpl: DeletePlaylistVideoUseCaseImpl
    ): DeletePlaylistVideoUseCase

    @Binds
    abstract fun bindExistsPlaylistVideo(
        existsPlaylistVideoByIdUseCaseImpl: ExistsPlaylistVideoByIdUseCaseImpl
    ): ExistsPlaylistVideoByIdUseCase

    @Binds
    abstract fun bindGetAllSavedVideoUseCase(
        getAllSavedVideoUseCaseImpl: GetAllSavedVideoUseCaseImpl
    ): GetAllSavedVideoUseCase

    @Binds
    abstract fun bindGetAllPlaylistVideoUseCase(
        getAllPlaylistVideoUseCaseImpl: GetAllPlaylistVideoUseCaseImpl
    ): GetAllPlaylistVideoUseCase
}