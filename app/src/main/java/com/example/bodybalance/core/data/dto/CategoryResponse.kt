package com.example.bodybalance.core.data.dto

class CategoryResponse(
    val id: Int,
    val category: String,
    val videoItems: List<VideoDto>,
)