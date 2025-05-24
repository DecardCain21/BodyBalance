package com.example.bodybalance.core.domain.usecase.di

import com.example.bodybalance.core.domain.usecase.api.FollowTheLinkUseCase
import com.example.bodybalance.core.domain.usecase.api.ShareContentUseCase
import com.example.bodybalance.core.domain.usecase.impl.FollowTheLinkUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.ShareContentUseCaseImpl
import com.example.bodybalance.core.domain.usecase.api.GetVideoByCategoryUseCase
import com.example.bodybalance.core.domain.usecase.impl.GetVideoByCategoryUseCaseImpl
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

    @Binds
    abstract fun bindShareContentUseCase(
        shareContentUseCase: ShareContentUseCaseImpl
    ): ShareContentUseCase

    @Binds
    abstract fun bindGetVideoUseCase(
        getVideoByCategoryUseCaseImpl: GetVideoByCategoryUseCaseImpl
    ): GetVideoByCategoryUseCase
}