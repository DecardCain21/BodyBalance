package com.example.bodybalance.settings.di

import com.example.bodybalance.settings.domain.usecase.LogOutOfAccountUseCase
import com.example.bodybalance.core.domain.usecase.api.ShareContentUseCase
import com.example.bodybalance.settings.domain.usecase.impl.LogOutOfAccountUseCaseUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.ShareContentUseCaseImpl
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
}