package com.example.bodybalance.core.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bodybalance.core.data.source.local.database.entity.SavedVideo

@Dao
interface VideoCacheDao {
    // Вставка или обновление записи
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedVideo: SavedVideo)

    // Удаление записи по объекту
    @Delete
    suspend fun delete(savedVideo: SavedVideo)

    // Удаление записи по id
    @Query("DELETE FROM saved_video WHERE id = :id")
    suspend fun deleteById(id: Double)

    // Поиск записи по id
    @Query("SELECT * FROM saved_video WHERE id = :id")
    suspend fun getById(id: Double): SavedVideo?

    // Получение всех записей
    @Query("SELECT * FROM saved_video")
    suspend fun getAll(): List<SavedVideo>

    // Поиск записей по категории
    @Query("SELECT * FROM saved_video WHERE category = :category")
    suspend fun getByCategory(category: String): List<SavedVideo>

    // Обновление записи
    @Update
    suspend fun update(savedVideo: SavedVideo)
}