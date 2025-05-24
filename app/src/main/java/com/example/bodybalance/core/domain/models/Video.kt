package com.example.bodybalance.core.domain.models

data class Video(
    val id: Int,
    val name: String,
    val url: String,
    val category: String,
    val description: String,
    val imageUrl: String = ""
) {

    companion object {
        fun emptyVideo() = Video(
            id = 0,
            url = "",
            category = "",
            name = "Разминка перед упражнениями на отдельную группу мыщц",
            description = ""
        )
    }
}