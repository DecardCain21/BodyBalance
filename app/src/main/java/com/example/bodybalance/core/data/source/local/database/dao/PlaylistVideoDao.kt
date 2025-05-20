package com.example.bodybalance.core.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.bodybalance.core.data.source.local.database.entity.PlaylistVideoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaylistVideoDao {

    @Query("SELECT * FROM playlist_video ORDER BY `order` ASC")
    fun getAllPlaylistVideos(): Flow<List<PlaylistVideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistVideo(entity: PlaylistVideoEntity)

    @Delete
    suspend fun deletePlaylistVideo(entity: PlaylistVideoEntity)

    @Query("DELETE FROM playlist_video WHERE id = :id")
    suspend fun deletePlaylistVideoById(id: Double)

    @Query("SELECT * FROM playlist_video WHERE id = :id")
    suspend fun getPlaylistVideoById(id: Double): PlaylistVideoEntity?

    @Update
    suspend fun updatePlaylistVideo(entity: PlaylistVideoEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM playlist_video WHERE id = :id LIMIT 1)")
    suspend fun existsById(id: Double): Boolean

    @Query("UPDATE playlist_video SET `order` = :order WHERE id = :id")
    suspend fun updateOrder(id: Double, order: Int)
}
