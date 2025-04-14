package com.example.bodybalance.core.domain.models

data class Video(
    val id: Double,
    val title: String,
    val url: String,
    val category: String? = "",
    val description: String,
    val imageUrl: String? = ""
) {

    companion object {
        fun emptyVideo() = Video(
            id = 0.0,
            url = "",
            category = null,
            title = "",
            description = ""
        )
    }
}