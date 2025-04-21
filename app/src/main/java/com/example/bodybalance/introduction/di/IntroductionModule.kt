package com.example.bodybalance.introduction.di

import com.example.bodybalance.introduction.domain.usecase.GetIntroductionCodeUseCase
import com.example.bodybalance.introduction.domain.usecase.SetIntroductionCodeUseCase
import com.example.bodybalance.introduction.domain.usecase.impl.GetIntroductionCodeUseCaseImpl
import com.example.bodybalance.introduction.domain.usecase.impl.SetIntroductionCodeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class IntroductionModuleBinder {

    @Binds
    abstract fun bindGetIntroductionCodeUseCase(
        getIntroductionCodeUseCase: GetIntroductionCodeUseCaseImpl
    ): GetIntroductionCodeUseCase

    @Binds
    abstract fun bindSetIntroductionCodeUseCase(
        setIntroductionCodeUseCase: SetIntroductionCodeUseCaseImpl
    ): SetIntroductionCodeUseCase
}