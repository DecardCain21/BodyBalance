package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.dto.VideoDto
import com.example.bodybalance.core.domain.models.Video

internal fun Video.convertToDto() = VideoDto(
    id = id,
    name = name,
    url = remoteVideoUrl,
    description = description,
    category = category,
    imageUrl = imageUrl
)

internal fun VideoDto.convertToVideo() = Video(
    id = id,
    name = name,
    remoteVideoUrl = url,
    description = description,
    category = category,
    imageUrl = imageUrl
)