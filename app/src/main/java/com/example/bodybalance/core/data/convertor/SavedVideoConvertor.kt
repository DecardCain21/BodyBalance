package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.db.entity.SavedVideo
import com.example.bodybalance.core.domain.models.Video

fun Video.convertToSavedVideo() = SavedVideo(
    id = id,
    videoUrl = url,
    category = category
)

fun SavedVideo.convertToVideo() = Video(
    id = id,
    url = videoUrl,
    category = category,
    name = "",
    description = ""
)