package com.example.bodybalance.core.domain.models

public data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String,
) {
    public companion object {
        public fun empty(): Category = Category(id = 0, name = "", imageUrl = "")
    }
}