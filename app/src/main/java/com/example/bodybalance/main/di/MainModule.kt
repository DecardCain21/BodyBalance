package com.example.bodybalance.main.di

import com.example.bodybalance.main.domain.usecase.CheckAuthUseCase
import com.example.bodybalance.main.domain.usecase.impl.CheckAuthUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MainModuleBinder {

    @Binds
    abstract fun bindCheckAuthUseCase(
        checkAuthUseCase: CheckAuthUseCaseImpl
    ): CheckAuthUseCase
}