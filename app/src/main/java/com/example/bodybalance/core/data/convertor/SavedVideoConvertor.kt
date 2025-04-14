package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.source.local.database.entity.SavedVideo
import com.example.bodybalance.core.domain.models.Video

fun Video.convertToSavedVideo() = SavedVideo(
    id = id,
    title = title,
    videoUrl = url,
    category = category,
    description = description
)

fun SavedVideo.convertToVideo() = Video(
    id = id,
    title = title,
    url = videoUrl,
    category = category ?: "category",
    description = description ?: "description"
)