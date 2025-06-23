package com.example.bodybalance.core.data.source.network.di

import android.content.Context
import com.example.bodybalance.core.data.source.network.BodyBalanceApiService
import com.example.bodybalance.core.data.source.network.client.CategoryNetworkClient
import com.example.bodybalance.core.data.source.network.client.LoginNetworkClient
import com.example.bodybalance.core.data.source.network.client.VideoNetworkClient
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

internal const val BODY_BALANCE_BASE_URL = "https://body.7375.org"
// const val BODY_BALANCE_BASE_URL = "https://body-balance-backend.onrender.com/"

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModuleProvider {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

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

    @Provides
    @Singleton
    fun provideCategoryNetworkClient(
        binListApiService: BodyBalanceApiService
    ): CategoryNetworkClient {
        return CategoryNetworkClient(binListApiService)
    }

    @Provides
    @Singleton
    fun provideLoginNetworkClient(
        binListApiService: BodyBalanceApiService,
        firebaseAnalytics: FirebaseAnalytics
    ): LoginNetworkClient {
        return LoginNetworkClient(binListApiService, firebaseAnalytics)
    }

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(@ApplicationContext context: Context): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(context)
    }
}