package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.source.local.database.entity.PlaylistVideoEntity
import com.example.bodybalance.core.domain.models.Video

fun Video.convertEntity() = PlaylistVideoEntity(
    id = id,
    title = title,
    url = url,
    category = category,
    description = description,
    imageUrl = imageUrl,
)

fun PlaylistVideoEntity.convertToVideo() = Video(
    id = id,
    title = title,
    url = url,
    category = category,
    description = description,
    imageUrl = imageUrl
)