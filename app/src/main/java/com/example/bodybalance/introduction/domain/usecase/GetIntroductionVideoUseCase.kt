package com.example.bodybalance.introduction.domain.usecase

public interface GetIntroductionVideoUseCase {

    public operator fun invoke(): String

    public fun unpackVideoIfNeeded(): Boolean
}