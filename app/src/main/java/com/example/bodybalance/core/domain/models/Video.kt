package com.example.bodybalance.core.domain.models

public data class Video(
    val id: Int,
    val name: String,
    val url: String,
    val category: String,
    val description: String,
    val imageUrl: String = ""
) {

    public companion object {
        public fun emptyVideo(id: Int): Video = Video(
            id = id,
            url = "",
            category = "",
            name = "",
            description = ""
        )
    }
}