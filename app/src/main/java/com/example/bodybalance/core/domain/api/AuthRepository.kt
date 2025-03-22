package com.example.bodybalance.core.domain.api

interface AuthRepository {

    fun isAuthenticated(): Boolean

    fun setAuthenticated(value: Boolean)
}