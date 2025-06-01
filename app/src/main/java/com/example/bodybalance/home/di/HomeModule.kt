package com.example.bodybalance.home.di

import com.example.bodybalance.home.domain.usecase.CheckLoginUseCase
import com.example.bodybalance.home.domain.usecase.impl.CheckLoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeModuleBinder {

    @Binds
    abstract fun bindCheckLoginUseCase(
        checkLoginUseCase: CheckLoginUseCaseImpl
    ): CheckLoginUseCase
}