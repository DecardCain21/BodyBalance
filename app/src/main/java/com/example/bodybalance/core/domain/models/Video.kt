package com.example.bodybalance.core.domain.models

public data class Video(
    val id: Int,
    val name: String,
    val category: String,
    val description: String,
    val remoteVideoUrl: String,
    val localVideoUrl: String = "",
    val imageUrl: String = ""
) {

    public companion object {
        public fun emptyVideo(id: Int): Video = Video(
            id = id,
            remoteVideoUrl = "",
            category = "",
            name = "",
            description = ""
        )
    }
}