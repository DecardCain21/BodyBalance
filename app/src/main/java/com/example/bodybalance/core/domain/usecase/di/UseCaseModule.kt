package com.example.bodybalance.core.domain.usecase.di

import com.example.bodybalance.core.domain.usecase.api.DeleteSavedVideoUseCase
import com.example.bodybalance.core.domain.usecase.api.FollowLinkUseCase
import com.example.bodybalance.core.domain.usecase.api.GetAllPlaylistVideosUseCase
import com.example.bodybalance.core.domain.usecase.api.GetVideoByCategoryUseCase
import com.example.bodybalance.core.domain.usecase.api.GetVideoByIdUseCase
import com.example.bodybalance.core.domain.usecase.api.SaveVideoInCacheUseCase
import com.example.bodybalance.core.domain.usecase.api.ShareContentUseCase
import com.example.bodybalance.core.domain.usecase.impl.DeleteSavedVideoUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.FollowLinkUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.GetAllPlaylistVideosUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.GetVideoByCategoryUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.GetVideoByIdUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.SaveVideoInCacheUseCaseImpl
import com.example.bodybalance.core.domain.usecase.impl.ShareContentUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
public abstract class UseCaseBinder {

    @Binds
    public abstract fun bindFollowTheLinkUseCase(
        followTheLinkUseCase: FollowLinkUseCaseImpl
    ): FollowLinkUseCase

    @Binds
    public abstract fun bindShareContentUseCase(
        shareContentUseCase: ShareContentUseCaseImpl
    ): ShareContentUseCase

    @Binds
    public abstract fun bindGetVideoUseCase(
        getVideoByCategoryUseCaseImpl: GetVideoByCategoryUseCaseImpl
    ): GetVideoByCategoryUseCase

    @Binds
    public abstract fun bindGetAllVideosUseCaseImpl(
        getAllSavedVideosUseCaseImpl: GetAllPlaylistVideosUseCaseImpl
    ): GetAllPlaylistVideosUseCase

    @Binds
    public abstract fun bindGetVideoByIdUseCase(
        getVideoByIdUseCaseImpl: GetVideoByIdUseCaseImpl
    ): GetVideoByIdUseCase

    @Binds
    public abstract fun bindSaveVideoInCache(
        saveVideoInCacheImpl: SaveVideoInCacheUseCaseImpl
    ): SaveVideoInCacheUseCase

    @Binds
    public abstract fun bindDeleteSavedVideoUseCase(
        deleteSavedVideoUseCaseImpl: DeleteSavedVideoUseCaseImpl
    ): DeleteSavedVideoUseCase
}