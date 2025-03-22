package com.example.bodybalance.core.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bodybalance.core.data.source.local.database.dao.VideoCacheDao
import com.example.bodybalance.core.data.source.local.database.entity.SavedVideo

@Database(
    version = 1,
    entities = [SavedVideo::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun videoCacheDao(): VideoCacheDao
}