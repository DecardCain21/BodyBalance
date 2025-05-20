package com.example.bodybalance.category.di

import com.example.bodybalance.category.domain.usecase.ActivateAccountUseCase
import com.example.bodybalance.category.domain.usecase.GetAllAccountsUseCase
import com.example.bodybalance.category.domain.usecase.GetAllPlaylistVideosUseCase
import com.example.bodybalance.category.domain.usecase.GetCategoryUseCase
import com.example.bodybalance.category.domain.usecase.impl.ActivateAccountUseCaseImpl
import com.example.bodybalance.category.domain.usecase.impl.GetAllAccountsUseCaseImpl
import com.example.bodybalance.category.domain.usecase.impl.GetAllPlaylistVideosUseCaseImpl
import com.example.bodybalance.category.domain.usecase.impl.GetCategoryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryModuleBinder {

    @Binds
    abstract fun bindGetSectionsUseCase(
        getSectionsUseCase: GetCategoryUseCaseImpl
    ): GetCategoryUseCase

    @Binds
    abstract fun bindGetAllVideosUseCaseImpl(
        getAllSavedVideosUseCaseImpl: GetAllPlaylistVideosUseCaseImpl
    ): GetAllPlaylistVideosUseCase

    @Binds
    abstract fun bindActivateAccountUseCaseImpl(
        activateAccountUseCaseImpl: ActivateAccountUseCaseImpl
    ): ActivateAccountUseCase

    @Binds
    abstract fun bindGetAllAccountsUseCaseImpl(
        getAllAccountsUseCaseImpl: GetAllAccountsUseCaseImpl
    ): GetAllAccountsUseCase
}