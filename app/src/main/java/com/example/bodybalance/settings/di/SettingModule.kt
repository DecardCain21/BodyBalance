package com.example.bodybalance.settings.di

import com.example.bodybalance.settings.domain.usecase.ClearCacheUseCase
import com.example.bodybalance.settings.domain.usecase.GetFilesCacheSizeUseCase
import com.example.bodybalance.settings.domain.usecase.LogOutOfAccountUseCase
import com.example.bodybalance.settings.domain.usecase.impl.ClearCacheUseCaseImpl
import com.example.bodybalance.settings.domain.usecase.impl.GetFilesCacheSizeUseCaseImpl
import com.example.bodybalance.settings.domain.usecase.impl.LogOutOfAccountUseCaseUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModuleBinder {

    @Binds
    abstract fun bindLogOutOfAccountUseCase(
        logOutOfAccount: LogOutOfAccountUseCaseUseCaseImpl
    ): LogOutOfAccountUseCase

    @Binds
    abstract fun bindClearCacheUseCase(
        clearCacheUseCase: ClearCacheUseCaseImpl
    ): ClearCacheUseCase

    @Binds
    abstract fun bindGetFilesCacheSizeUseCase(
        getFilesCacheSizeUseCase: GetFilesCacheSizeUseCaseImpl
    ): GetFilesCacheSizeUseCase
}