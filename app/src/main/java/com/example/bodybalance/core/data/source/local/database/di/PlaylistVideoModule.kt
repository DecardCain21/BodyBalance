package com.example.bodybalance.core.data.source.local.database.di

import com.example.bodybalance.core.data.source.local.database.AppDatabase
import com.example.bodybalance.core.data.source.local.database.api.PlaylistVideoLocalSource
import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.data.source.local.database.impl.PlaylistVideoLocalSourceImpl
import com.example.bodybalance.core.data.source.local.database.impl.UserAccountLocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class PlaylistVideoBinder {

    @Binds
    abstract fun bindPlaylistVideoLocalSource(
        playlistVideoLocalSourceImpl: PlaylistVideoLocalSourceImpl
    ): PlaylistVideoLocalSource
}

@Module
@InstallIn(SingletonComponent::class)
internal object PlaylistVideoProvider {

    @Provides
    @Singleton
    fun providePlaylistVideoDao(appDatabase: AppDatabase) =
        appDatabase.playListVideoDao()
}