package com.example.bodybalance.core.domain.models

data class Video(
    val id: Double,
    val url: String,
    val category: String? = "",
    val name: String,
    val description: String
)