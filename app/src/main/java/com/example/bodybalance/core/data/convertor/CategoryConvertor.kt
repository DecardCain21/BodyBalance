package com.example.bodybalance.core.data.convertor

import com.example.bodybalance.core.data.dto.CategoryDto
import com.example.bodybalance.core.domain.models.Category

fun CategoryDto.convertToCategory() = Category(
    id = this.id,
    name = this.name,
)

fun Category.convertToCategory() = CategoryDto(
    id = this.id,
    name = this.name,
)