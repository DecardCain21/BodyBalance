package com.example.bodybalance.settings.di

import com.example.bodybalance.settings.domain.usecase.LogOutOfAccount
import com.example.bodybalance.settings.domain.usecase.impl.LogOutOfAccountImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsModuleBinder {

    @Binds
    abstract fun bindLogOutOfAccountUseCase(
        logOutOfAccount: LogOutOfAccountImpl
    ): LogOutOfAccount
}