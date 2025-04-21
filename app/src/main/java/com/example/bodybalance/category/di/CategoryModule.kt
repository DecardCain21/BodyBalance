package com.example.bodybalance.category.di

import com.example.bodybalance.category.domain.usecase.GetAllSavedVideosUseCase
import com.example.bodybalance.category.domain.usecase.GetCategoryUseCase
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
}