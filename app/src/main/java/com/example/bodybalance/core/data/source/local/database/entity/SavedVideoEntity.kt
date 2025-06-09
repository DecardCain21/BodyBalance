package com.example.bodybalance.core.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_video")
internal data class SavedVideoEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val remoteVideoUrl: String,
    val localVideoUrl: String,
    val description: String,
    val category: String = "",
    val imageUrl: String = "",
    val order: Int
)