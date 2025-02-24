package com.example.bodybalance.videoplayer.di

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.example.bodybalance.core.util.ExoPlayerCache
import com.example.bodybalance.videoplayer.domain.usecase.GetVideoUseCase
import com.example.bodybalance.videoplayer.domain.usecase.impl.GetVideoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VideoPlaylistModuleBinder {

    @Binds
    abstract fun bindGetVideoUseCase(
        getVideoUseCaseImpl: GetVideoUseCaseImpl
    ) : GetVideoUseCase
}