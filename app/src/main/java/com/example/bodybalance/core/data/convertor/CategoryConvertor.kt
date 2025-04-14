package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.dto.CategoryResponse
import com.example.bodybalance.core.data.dto.VideoDto
import com.example.bodybalance.core.domain.models.Category
import com.example.bodybalance.core.domain.models.Video

fun CategoryResponse.convertToCategory() = Category(
    id = this.id,
    category = this.category,
    videoItems = this.videoItems.map { videoDto ->
        videoDto.convertToVideo()
    }
)

fun VideoDto.convertToVideo() = Video(
    id = id,
    url = url,
    category = category,
    title = name,
    description = description
)