package com.example.bodybalance.settings.domain.usecase.impl

import com.example.bodybalance.core.domain.api.ExternalNavigator
import com.example.bodybalance.settings.domain.usecase.ShareContentUseCase
import javax.inject.Inject

class ShareContentUseCaseImpl @Inject constructor(
    private val externalNavigator: ExternalNavigator
): ShareContentUseCase {

    override fun invoke(text: String, title: String) {externalNavigator.share(text, title)}
}