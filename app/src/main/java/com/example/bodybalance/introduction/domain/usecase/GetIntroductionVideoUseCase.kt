package com.example.bodybalance.introduction.domain.usecase

interface GetIntroductionVideoUseCase {
    public operator fun invoke(): String

    public fun unpackVideoIfNeeded():Boolean
}