package com.example.bodybalance.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bodybalance.core.data.db.dao.VideoCacheDao
import com.example.bodybalance.core.data.db.entity.SavedVideo

@Database(
    version = 1,
    entities = [SavedVideo::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun videoCacheDao(): VideoCacheDao
}