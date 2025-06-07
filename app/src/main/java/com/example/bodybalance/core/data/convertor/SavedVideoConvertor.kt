package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.source.local.database.entity.SavedVideoEntity
import com.example.bodybalance.core.domain.models.Video

internal fun Video.convertToSavedVideo() = SavedVideoEntity(
    id = id,
    name = name,
    videoUrl = url,
    category = category,
    description = description,
    order = 0
)

internal fun SavedVideoEntity.convertToVideo() = Video(
    id = id,
    name = name,
    url = videoUrl,
    category = category,
    description = description
)