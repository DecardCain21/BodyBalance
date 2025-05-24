package com.example.bodybalance.core.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bodybalance.core.data.source.local.database.entity.SavedVideoEntity

@Dao
interface VideoCacheDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedVideoEntity: SavedVideoEntity)

    @Delete
    suspend fun delete(savedVideoEntity: SavedVideoEntity)

    @Query("DELETE FROM saved_video WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM saved_video WHERE id = :id")
    suspend fun getById(id: Int): SavedVideoEntity?

    @Query("SELECT * FROM saved_video")
    suspend fun getAll(): List<SavedVideoEntity>

    @Query("SELECT * FROM saved_video WHERE name = :category")
    suspend fun getByCategory(category: String): List<SavedVideoEntity>

    @Update
    suspend fun update(savedVideoEntity: SavedVideoEntity)
}