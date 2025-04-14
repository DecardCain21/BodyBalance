package com.example.bodybalance.core.data.source.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_video")
data class SavedVideo(
    @PrimaryKey
    val id: Double,
    val title: String,
    val videoUrl: String,
    val description: String,
    val category: String? = "",
    val imageUrl: String? = ""
)