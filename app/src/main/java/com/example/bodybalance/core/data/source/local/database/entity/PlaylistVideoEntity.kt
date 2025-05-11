package com.example.bodybalance.core.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playlist_video")
data class PlaylistVideoEntity(
    @PrimaryKey
    val id: Double
)
