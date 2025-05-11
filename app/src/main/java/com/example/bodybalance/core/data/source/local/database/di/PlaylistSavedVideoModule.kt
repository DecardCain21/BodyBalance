package com.example.bodybalance.core.data.source.local.database.di

import com.example.bodybalance.core.data.source.local.database.AppDatabase
import com.example.bodybalance.core.data.source.local.database.dao.PlaylistVideoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PlaylistVideoDaoProvider {

    @Provides
    @Singleton
    fun providePlaylistSavedVideoDao(appDatabase: AppDatabase): PlaylistVideoDao {
        return appDatabase.playListVideoDao()
    }
}