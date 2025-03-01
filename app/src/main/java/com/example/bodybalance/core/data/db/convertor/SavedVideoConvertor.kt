package com.example.bodybalance.core.data.db.convertor

import com.example.bodybalance.core.data.db.entity.SavedVideo
import com.example.bodybalance.core.domain.models.Video


fun Video.convertToSavedVideo() = SavedVideo(
    id = this.id,
    videoUrl = this.previewUrl,
    category = this.category
)

fun SavedVideo.convertToVideo() = Video(
    id = this.id,
    previewUrl = this.videoUrl,
    category = this.category
)
