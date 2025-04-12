package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.source.local.database.entity.SavedVideo
import com.example.bodybalance.core.domain.models.Video

fun Video.convertToSavedVideo() = SavedVideo(
    id = id,
    videoUrl = url,
    title = category
)

fun SavedVideo.convertToVideo() = Video(
    id = id,
    url = videoUrl,
    category = title,
    name = "",
    description = ""
)