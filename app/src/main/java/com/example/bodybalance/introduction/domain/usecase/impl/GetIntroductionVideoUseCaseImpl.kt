package com.example.bodybalance.introduction.domain.usecase.impl

import com.example.bodybalance.core.data.source.local.IntroProvider
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.introduction.domain.usecase.GetIntroductionVideoUseCase
import javax.inject.Inject

public class GetIntroductionVideoUseCaseImpl @Inject constructor(
    private val introProvider: IntroProvider
) : GetIntroductionVideoUseCase {

    public override operator fun invoke(): Video {
        return introProvider.getVideo()
    }
}