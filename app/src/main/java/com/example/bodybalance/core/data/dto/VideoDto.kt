package com.example.bodybalance.core.data.dto

import com.google.gson.annotations.SerializedName

public class VideoDto(
    @SerializedName("id")
    public val id: Int,
    @SerializedName("url")
    public val url: String,
    @SerializedName("name")
    public val name: String,
    @SerializedName("description")
    public val description: String,
    @SerializedName("category")
    public val category: String,
    @SerializedName("img_url")
    public val imageUrl: String,
)