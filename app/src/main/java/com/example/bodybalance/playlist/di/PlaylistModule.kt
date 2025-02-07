package com.example.bodybalance.playlist.di

import com.example.bodybalance.playlist.domain.GetSectionsUseCase
import com.example.bodybalance.playlist.domain.usecase.impl.GetSectionsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PlaylistModule {

    @Binds
    abstract fun bindGetSectionsUseCase(
        getSectionsUseCase: GetSectionsUseCaseImpl
    ): GetSectionsUseCase
}