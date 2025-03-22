package com.example.bodybalance.core.data.source.local.database.di

import android.content.Context
import androidx.room.Room
import com.example.bodybalance.core.data.source.local.database.AppDatabase
import com.example.bodybalance.core.data.source.local.database.dao.VideoCacheDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBaseBuilder(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "database.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideSavedVideoDao(appDatabase: AppDatabase): VideoCacheDao {
        return appDatabase.videoCacheDao()
    }
}