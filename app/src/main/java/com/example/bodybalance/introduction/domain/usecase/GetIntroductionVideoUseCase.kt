package com.example.bodybalance.introduction.domain.usecase

import com.example.bodybalance.core.domain.models.Video

public interface GetIntroductionVideoUseCase {

    public operator fun invoke(): Video
}