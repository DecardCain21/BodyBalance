package com.example.bodybalance.core.domain.models

data class Category(
    val id: Int,
    val name: String,
) {
    companion object {
        fun empty() = Category(id = 0, name = "")
    }
}