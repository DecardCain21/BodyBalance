package com.example.bodybalance.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_video")
data class SavedVideo(
    @PrimaryKey
    val id: Int,
    val videoUrl: String,
    val category: String
)
