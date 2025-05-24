package com.example.bodybalance.core.data.dto

import com.google.gson.annotations.SerializedName

class VideoDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("category")
    val category: String,
)
