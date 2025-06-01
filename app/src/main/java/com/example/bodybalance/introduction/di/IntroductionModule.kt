package com.example.bodybalance.introduction.di

import android.content.Context
import com.example.bodybalance.core.data.source.local.IntroProvider
import com.example.bodybalance.introduction.domain.usecase.GetIntroductionCodeUseCase
import com.example.bodybalance.introduction.domain.usecase.GetIntroductionVideoUseCase
import com.example.bodybalance.introduction.domain.usecase.SetIntroductionCodeUseCase
import com.example.bodybalance.introduction.domain.usecase.impl.GetIntroductionCodeUseCaseImpl
import com.example.bodybalance.introduction.domain.usecase.impl.GetIntroductionVideoUseCaseImpl
import com.example.bodybalance.introduction.domain.usecase.impl.SetIntroductionCodeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class IntroductionModuleBinder {

    @Binds
    abstract fun bindGetIntroductionCodeUseCase(
        getIntroductionCodeUseCase: GetIntroductionCodeUseCaseImpl
    ): GetIntroductionCodeUseCase

    @Binds
    abstract fun bindSetIntroductionCodeUseCase(
        setIntroductionCodeUseCase: SetIntroductionCodeUseCaseImpl
    ): SetIntroductionCodeUseCase

    @Binds
    abstract fun bindGetIntroductionVideoUseCase(
        getIntroductionVideoUseCase: GetIntroductionVideoUseCaseImpl
    ): GetIntroductionVideoUseCase
}

@Module
@InstallIn(SingletonComponent::class)
internal object IntroductionModuleProvider {

    @Provides
    @Singleton
    fun provideIntroProvider(
        @ApplicationContext context: Context
    ): IntroProvider {
        return IntroProvider(context = context)
    }
}