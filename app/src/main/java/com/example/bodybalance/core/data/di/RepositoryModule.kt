package com.example.bodybalance.core.data.di

import com.example.bodybalance.core.data.repository.AuthRepositoryImpl
import com.example.bodybalance.core.data.repository.CategoryRepositoryImpl
import com.example.bodybalance.core.data.repository.IntroductionCodeRepositoryImpl
import com.example.bodybalance.core.data.repository.LoginRepositoryImpl
import com.example.bodybalance.core.data.repository.SavedVideoRepositoryImpl
import com.example.bodybalance.core.data.repository.VideoRepositoryImpl
import com.example.bodybalance.core.domain.api.AuthRepository
import com.example.bodybalance.core.domain.api.CategoryRepository
import com.example.bodybalance.core.domain.api.IntroductionCodeRepository
import com.example.bodybalance.core.domain.api.LoginRepository
import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.domain.api.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModuleBinder {

    @Binds
    abstract fun bindVideoRepository(
        videoRepositoryImpl: VideoRepositoryImpl
    ): VideoRepository

    @Binds
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    abstract fun bindLoginRepository(
        loginRepositoryImpl: LoginRepositoryImpl
    ): LoginRepository

    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindSaveVideoRepository(
        savedVideoRepositoryImpl: SavedVideoRepositoryImpl
    ): SavedVideoRepository

    @Binds
    abstract fun bindIntroductionCodeRepository(
        introductionCodeRepositoryImpl: IntroductionCodeRepositoryImpl
    ): IntroductionCodeRepository

}