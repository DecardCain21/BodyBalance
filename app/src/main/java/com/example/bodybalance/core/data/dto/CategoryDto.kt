package com.example.bodybalance.core.data.dto

import com.google.gson.annotations.SerializedName

public class CategoryDto(
    @SerializedName("id")
    public val id: Int,
    @SerializedName("name")
    public val name: String,
)