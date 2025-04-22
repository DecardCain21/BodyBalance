package com.example.bodybalance.core.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bodybalance.core.data.source.local.database.dao.UserAccountDao
import com.example.bodybalance.core.data.source.local.database.dao.VideoCacheDao
import com.example.bodybalance.core.data.source.local.database.entity.AccountEntity
import com.example.bodybalance.core.data.source.local.database.entity.SavedVideoEntity

@Database(
    version = 1,
    entities = [SavedVideoEntity::class, AccountEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun videoCacheDao(): VideoCacheDao

    abstract fun useAccountDaoDao(): UserAccountDao
}