package com.example.bodybalance.category.di

import com.example.bodybalance.category.domain.usecase.ActivateAccountUseCase
import com.example.bodybalance.category.domain.usecase.DeleteAccountUseCase
import com.example.bodybalance.category.domain.usecase.GetAllAccountsUseCase
import com.example.bodybalance.category.domain.usecase.GetAllSavedVideosUseCase
import com.example.bodybalance.category.domain.usecase.GetCategoryUseCase
import com.example.bodybalance.category.domain.usecase.impl.ActivateAccountUseCaseImpl
import com.example.bodybalance.category.domain.usecase.impl.DeleteAccountUseCaseImpl
import com.example.bodybalance.category.domain.usecase.impl.GetAllAccountsUseCaseImpl
import com.example.bodybalance.category.domain.usecase.impl.GetAllSavedVideosUseCaseImpl
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
        getAllSavedVideosUseCaseImpl: GetAllSavedVideosUseCaseImpl
    ): GetAllSavedVideosUseCase

    @Binds
    abstract fun bindActivateAccountUseCaseImpl(
        activateAccountUseCaseImpl: ActivateAccountUseCaseImpl
    ): ActivateAccountUseCase

    @Binds
    abstract fun bindDeleteAccountUseCaseImpl(
        deleteAccountUseCaseImpl: DeleteAccountUseCaseImpl
    ): DeleteAccountUseCase

    @Binds
    abstract fun bindGetAllAccountsUseCaseImpl(
        getAllAccountsUseCaseImpl: GetAllAccountsUseCaseImpl
    ): GetAllAccountsUseCase
}