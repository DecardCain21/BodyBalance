package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.dto.VideoDto
import com.example.bodybalance.core.domain.models.Video

internal fun Video.convertToDto() = VideoDto(
    id = id,
    name = name,
    url = url,
    description = description,
    category = category
)

internal fun VideoDto.convertToVideo() = Video(
    id = id,
    name = name,
    url = url,
    description = description,
    category = category
)