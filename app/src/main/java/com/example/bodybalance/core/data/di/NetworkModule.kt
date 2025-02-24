package com.example.bodybalance.core.data.di

import com.example.bodybalance.core.data.network.BodyBalanceApiService
import com.example.bodybalance.core.data.network.VideoNetworkClient
import com.example.bodybalance.core.data.repository.VideoRepositoryImpl
import com.example.bodybalance.core.domain.api.VideoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BODY_BALANCE_BASE_URL = "https://body-balance-backend.onrender.com/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModuleProvider {

    @Provides
    @Singleton
    fun provideHeadHunterApiService(
    ): BodyBalanceApiService {
        return Retrofit.Builder().baseUrl(BODY_BALANCE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(BodyBalanceApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideVideoNetworkClient(
        binListApiService: BodyBalanceApiService
    ): VideoNetworkClient {
        return VideoNetworkClient(binListApiService)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModuleBinder {
    @Binds
    abstract fun bindVideoRepository(
        videoRepositoryImpl: VideoRepositoryImpl
    ): VideoRepository
}
