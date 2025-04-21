package com.example.bodybalance.introduction.domain.usecase.impl

import com.example.bodybalance.core.domain.api.IntroductionCodeRepository
import com.example.bodybalance.introduction.domain.usecase.SetIntroductionCodeUseCase
import javax.inject.Inject

class SetIntroductionCodeUseCaseImpl @Inject constructor(
    private val introductionCodeRepository: IntroductionCodeRepository
) : SetIntroductionCodeUseCase {

    override fun invoke(code: String) {
        introductionCodeRepository.setCode(code)
    }
}