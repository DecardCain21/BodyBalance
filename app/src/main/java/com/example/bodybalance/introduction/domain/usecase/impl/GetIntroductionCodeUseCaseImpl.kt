package com.example.bodybalance.introduction.domain.usecase.impl

import com.example.bodybalance.core.domain.api.IntroductionCodeRepository
import com.example.bodybalance.introduction.domain.usecase.GetIntroductionCodeUseCase
import javax.inject.Inject

class GetIntroductionCodeUseCaseImpl @Inject constructor(
    private val introductionCodeRepository: IntroductionCodeRepository
) : GetIntroductionCodeUseCase {

    override fun invoke(): String = introductionCodeRepository.getCode()
}