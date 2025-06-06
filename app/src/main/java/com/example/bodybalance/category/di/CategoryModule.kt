package com.example.bodybalance.category.di

import com.example.bodybalance.category.domain.usecase.ActivateAccountUseCase
import com.example.bodybalance.category.domain.usecase.GetAllAccountsUseCase
import com.example.bodybalance.category.domain.usecase.GetAllDownloadedFilesUseCase
import com.example.bodybalance.category.domain.usecase.GetCategoryUseCase
import com.example.bodybalance.category.domain.usecase.UpdateOrderPlaylistVideoUseCase
import com.example.bodybalance.category.domain.usecase.impl.ActivateAccountUseCaseImpl
import com.example.bodybalance.category.domain.usecase.impl.GetAllAccountsUseCaseImpl
import com.example.bodybalance.category.domain.usecase.impl.GetAllDownloadedFilesUseCaseImpl
import com.example.bodybalance.category.domain.usecase.impl.GetCategoryUseCaseImpl
import com.example.bodybalance.category.domain.usecase.impl.UpdateOrderPlaylistVideoUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CategoryModuleBinder {

    @Binds
    abstract fun bindGetSectionsUseCase(
        getSectionsUseCase: GetCategoryUseCaseImpl
    ): GetCategoryUseCase

    @Binds
    abstract fun bindActivateAccountUseCaseImpl(
        activateAccountUseCaseImpl: ActivateAccountUseCaseImpl
    ): ActivateAccountUseCase

    @Binds
    abstract fun bindGetAllAccountsUseCaseImpl(
        getAllAccountsUseCaseImpl: GetAllAccountsUseCaseImpl
    ): GetAllAccountsUseCase

    @Binds
    abstract fun bindUpdateOrderPlaylistVideoUseCaseImpl(
        updateOrderPlaylistVideoUseCaseImpl: UpdateOrderPlaylistVideoUseCaseImpl
    ): UpdateOrderPlaylistVideoUseCase

    @Binds
    abstract fun bindGetAllDownloadedFilesUseCase(
        getAllDownloadedFilesUseCaseImpl: GetAllDownloadedFilesUseCaseImpl
    ): GetAllDownloadedFilesUseCase
}