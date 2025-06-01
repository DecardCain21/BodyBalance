package com.example.bodybalance.introduction.domain.usecase

internal interface GetIntroductionVideoUseCase {

    public operator fun invoke(): String

    public fun unpackVideoIfNeeded(): Boolean
}