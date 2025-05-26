package com.example.bodybalance.core.domain.usecase.di

import com.example.bodybalance.core.domain.usecase.api.FollowLinkUseCase
import com.example.bodybalance.core.domain.usecase.api.GetAllPlaylistVideosUseCase
import com.example.bodybalance.core.domain.usecase.api.GetVideoByCategoryUseCase
import com.example.bodybalance.core.domain.usecase.api.GetVideoByIdUseCase
import com.example.bodybalance.core.domain.usecase.api.ShareContentUseCase
import com.example.bodybalance.core.domain.usecase.impl.FollowLinkUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.GetAllPlaylistVideosUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.GetVideoByCategoryUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.GetVideoByIdUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.ShareContentUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseBinder {

    @Binds
    abstract fun bindFollowTheLinkUseCase(
        followTheLinkUseCase: FollowLinkUseCaseImpl
    ): FollowLinkUseCase

    @Binds
    abstract fun bindShareContentUseCase(
        shareContentUseCase: ShareContentUseCaseImpl
    ): ShareContentUseCase

    @Binds
    abstract fun bindGetVideoUseCase(
        getVideoByCategoryUseCaseImpl: GetVideoByCategoryUseCaseImpl
    ): GetVideoByCategoryUseCase

    @Binds
    abstract fun bindGetAllVideosUseCaseImpl(
        getAllSavedVideosUseCaseImpl: GetAllPlaylistVideosUseCaseImpl
    ): GetAllPlaylistVideosUseCase

    @Binds
    abstract fun bindGetVideoByIdUseCase(
        getVideoByIdUseCaseImpl: GetVideoByIdUseCaseImpl
    ): GetVideoByIdUseCase
}