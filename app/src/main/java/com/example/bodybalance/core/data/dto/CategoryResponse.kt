package com.example.bodybalance.core.data.dto

class CategoryResponse(
    val id: Int,
    val category: String,
    val videoItems: List<VideoDto>,
)

class Test(
    val id: Int,
    val url: String,
    val name: String,
    val description: String,
    val category: String,
)

/*"id": 5,
        "url": "https://api.7375.org/video/Sheya_baza.mp4",
        "name": "Разминка для шеи",
        "description": "Простое упражнение для разминки шейного отдела",
        "category": "Шея"*/