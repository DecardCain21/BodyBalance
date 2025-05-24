package com.example.bodybalance.core.data.dto

import com.google.gson.annotations.SerializedName

class AccountDto(
    @SerializedName("type_id")
    val typeId: Int,
    @SerializedName("type_name")
    val typeName: String
)