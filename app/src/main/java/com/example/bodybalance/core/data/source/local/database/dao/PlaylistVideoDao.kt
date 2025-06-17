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
internal interface PlaylistVideoDao {

    @Query("SELECT * FROM playlist_video ORDER BY `order` ASC, timeOfAddition DESC")
    fun getAllPlaylistVideoFlow(): Flow<List<PlaylistVideoEntity>>

    @Query("SELECT * FROM playlist_video")
    suspend fun getAllPlaylistVideo():List<PlaylistVideoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylistVideo(entity: PlaylistVideoEntity)

    @Delete
    suspend fun deletePlaylistVideo(entity: PlaylistVideoEntity)

    @Query("DELETE FROM playlist_video WHERE id = :id")
    suspend fun deletePlaylistVideoById(id: Int)

    @Query("SELECT * FROM playlist_video WHERE id = :id")
    suspend fun getPlaylistVideoById(id: Int): PlaylistVideoEntity?

    @Update
    suspend fun updatePlaylistVideo(entity: PlaylistVideoEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM playlist_video WHERE id = :id LIMIT 1)")
    suspend fun existsById(id: Int): Boolean

    @Query("UPDATE playlist_video SET `order` = :order WHERE id = :id")
    suspend fun updateOrder(id: Int, order: Int)
}
