package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.source.local.storage.PreferencesStorage
import com.example.bodybalance.core.domain.api.IntroductionCodeRepository
import javax.inject.Inject

class IntroductionCodeRepositoryImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage
) : IntroductionCodeRepository {

    override fun getCode(): String = preferencesStorage.code

    override fun setCode(code: String) {
        preferencesStorage.code = code
    }
}