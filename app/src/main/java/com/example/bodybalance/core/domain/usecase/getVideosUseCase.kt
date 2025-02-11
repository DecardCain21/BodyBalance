package com.example.bodybalance.core.domain.usecase

import com.example.bodybalance.core.domain.models.AccountTypeTherapy
import com.example.bodybalance.core.domain.models.Video

class GetVideosUseCase {
    operator fun invoke(accountTypeTherapy: AccountTypeTherapy): List<Video> {
        return listOf()
    }
}