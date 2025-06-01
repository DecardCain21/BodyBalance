package com.example.bodybalance.core.data.dto

import com.google.gson.annotations.SerializedName

public class AccountDto(
    @SerializedName("type_id")
    public val typeId: Int,
    @SerializedName("type_name")
    public val typeName: String
)