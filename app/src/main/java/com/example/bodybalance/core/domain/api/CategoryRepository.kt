package com.example.bodybalance.core.domain.api

import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategory(): Flow<List<String>>
}