package com.example.bodybalance.core.data.dto

import com.google.gson.annotations.SerializedName

class CategoryDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
)