package com.example.bodybalance.core.domain.usecase.impl

import com.example.bodybalance.core.domain.api.ExternalNavigator
import com.example.bodybalance.core.domain.usecase.api.FollowLinkUseCase
import javax.inject.Inject

class FollowLinkUseCaseImpl @Inject constructor(
    private val externalNavigator: ExternalNavigator
): FollowLinkUseCase {

    override fun invoke(url: String) { externalNavigator.followTheLink(url) }
}