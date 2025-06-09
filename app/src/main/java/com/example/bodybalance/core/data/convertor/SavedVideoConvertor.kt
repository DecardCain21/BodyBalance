package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.source.local.database.entity.SavedVideoEntity
import com.example.bodybalance.core.domain.models.Video

internal fun Video.convertToSavedVideo() = SavedVideoEntity(
    id = id,
    name = name,
    remoteVideoUrl = remoteVideoUrl,
    localVideoUrl = localVideoUrl,
    category = category,
    description = description,
    imageUrl = imageUrl,
    order = 0
)

internal fun SavedVideoEntity.convertToVideo() = Video(
    id = id,
    name = name,
    remoteVideoUrl = remoteVideoUrl,
    localVideoUrl = localVideoUrl,
    category = category,
    description = description,
    imageUrl = imageUrl
)