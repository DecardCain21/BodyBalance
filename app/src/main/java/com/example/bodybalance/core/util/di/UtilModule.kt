package com.example.bodybalance.core.util.di

import android.content.Context
import com.example.bodybalance.core.util.FileDownloader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModuleProvider {

    @Provides
    @Singleton
    fun provideFileDownloader(
        @ApplicationContext context: Context
    ): FileDownloader = FileDownloader(context = context)

}