package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.source.local.database.entity.PlaylistVideoEntity
import com.example.bodybalance.core.domain.models.Video

internal fun Video.convertEntity() = PlaylistVideoEntity(
    id = id,
    name = name,
    url = url,
    category = category,
    description = description,
    imageUrl = imageUrl,
    order = 0
)

internal fun PlaylistVideoEntity.convertToVideo() = Video(
    id = id,
    name = name,
    url = url,
    category = category,
    description = description,
    imageUrl = imageUrl
)