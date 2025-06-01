package com.example.bodybalance.core.data.source.local.database.di

import com.example.bodybalance.core.data.source.local.database.AppDatabase
import com.example.bodybalance.core.data.source.local.database.api.UserAccountLocalSource
import com.example.bodybalance.core.data.source.local.database.dao.UserAccountDao
import com.example.bodybalance.core.data.source.local.database.impl.UserAccountLocalSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class UserAccountBinder {

    @Binds
    abstract fun bindUseAccountLocalSource(
        useAccountLocalSource: UserAccountLocalSourceImpl
    ): UserAccountLocalSource
}

@Module
@InstallIn(SingletonComponent::class)
internal object UserAccountProvider {

    @Provides
    @Singleton
    fun provideUseAccountDao(appDatabase: AppDatabase): UserAccountDao {
        return appDatabase.useAccountDaoDao()
    }
}