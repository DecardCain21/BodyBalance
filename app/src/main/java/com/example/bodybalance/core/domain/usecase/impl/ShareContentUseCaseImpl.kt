package com.example.bodybalance.core.domain.usecase.impl

import com.example.bodybalance.core.domain.api.ExternalNavigator
import com.example.bodybalance.core.domain.usecase.api.ShareContentUseCase
import javax.inject.Inject

class ShareContentUseCaseImpl @Inject constructor(
    private val externalNavigator: ExternalNavigator
): ShareContentUseCase {

    override fun invoke(text: String, title: String) {externalNavigator.share(text, title)}
}