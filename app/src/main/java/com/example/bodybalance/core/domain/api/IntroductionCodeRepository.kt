package com.example.bodybalance.core.domain.api

interface IntroductionCodeRepository {

    fun getCode(): String

    fun setCode(code: String)
}