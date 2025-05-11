package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.source.local.database.entity.PlaylistVideoEntity
import com.example.bodybalance.core.domain.models.Video

fun Video.convertToPlaylistVideo() = PlaylistVideoEntity(id = id)