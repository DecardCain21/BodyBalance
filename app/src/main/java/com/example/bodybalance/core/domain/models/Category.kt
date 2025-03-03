package com.example.bodybalance.core.domain.models

data class Category(
    val id: Int,
    val category: String,
    val videoItems: List<Video>,
)