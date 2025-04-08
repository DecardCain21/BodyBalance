package com.example.bodybalance.core.data.di

import com.example.bodybalance.core.data.service.ExternalNavigatorImpl
import com.example.bodybalance.core.domain.api.ExternalNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModuleBinder {

    @Binds
    abstract fun bindExternalNavigator(
        externalNavigatorImpl: ExternalNavigatorImpl
    ): ExternalNavigator

}