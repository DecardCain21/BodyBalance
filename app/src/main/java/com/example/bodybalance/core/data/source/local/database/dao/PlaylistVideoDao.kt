package com.example.bodybalance.core.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bodybalance.core.data.source.local.database.entity.PlaylistVideoEntity

@Dao
interface PlaylistVideoDao {

    // Добавление или обновление (если конфликт по PrimaryKey)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistVideo(entity: PlaylistVideoEntity)

    // Удаление по объекту
    @Delete
    suspend fun deletePlaylistVideo(entity: PlaylistVideoEntity)

    // Удаление по ID (альтернативный вариант)
    @Query("DELETE FROM playlist_video WHERE id = :id")
    suspend fun deletePlaylistVideoById(id: Double)

    // Получение по ID
    @Query("SELECT * FROM playlist_video WHERE id = :id")
    suspend fun getPlaylistVideoById(id: Double): PlaylistVideoEntity?

    // Получение всех элементов
    @Query("SELECT * FROM playlist_video")
    suspend fun getPlaylistAllVideos(): List<PlaylistVideoEntity>

    // Обновление существующей записи
    @Update
    suspend fun updatePlaylistVideo(entity: PlaylistVideoEntity)
}
