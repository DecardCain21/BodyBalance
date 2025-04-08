package com.example.bodybalance.core.domain.usecase.di

import com.example.bodybalance.core.domain.usecase.api.FollowTheLinkUseCase
import com.example.bodybalance.core.domain.usecase.impl.FollowTheLinkUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseBinder {

    @Binds
    abstract fun bindFollowTheLinkUseCase(
        followTheLinkUseCase: FollowTheLinkUseCaseImpl
    ): FollowTheLinkUseCase
}