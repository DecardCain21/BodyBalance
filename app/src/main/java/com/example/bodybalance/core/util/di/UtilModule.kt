package com.example.bodybalance.core.util.di

import android.content.Context
import com.example.bodybalance.core.domain.api.SettingsToolsRepository
import com.example.bodybalance.core.util.api.FileDownloader
import com.example.bodybalance.core.util.FileDownloaderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public object UtilModuleProvider {

    @Provides
    @Singleton
    public fun provideFileDownloader(
        @ApplicationContext context: Context,
        settingsToolsRepository: SettingsToolsRepository,
        okHttpClient: OkHttpClient
    ): FileDownloader = FileDownloaderImpl(
        context = context,
        settingsToolsRepository = settingsToolsRepository,
        okHttpClient = okHttpClient
    )
}