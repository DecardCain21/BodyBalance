package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.source.local.database.entity.SavedVideoEntity
import com.example.bodybalance.core.domain.models.Video

fun Video.convertToSavedVideo() = SavedVideoEntity(
    id = id,
    title = name,
    videoUrl = url,
    category = category,
    description = description
)

fun SavedVideoEntity.convertToVideo() = Video(
    id = id,
    name = title,
    url = videoUrl,
    category = category ?: "category",
    description = description ?: "description"
)