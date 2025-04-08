package com.example.bodybalance.core.domain.usecase.impl

import com.example.bodybalance.core.domain.api.ExternalNavigator
import com.example.bodybalance.core.domain.usecase.api.FollowTheLinkUseCase
import javax.inject.Inject

class FollowTheLinkUseCaseImpl @Inject constructor(
    private val externalNavigator: ExternalNavigator
): FollowTheLinkUseCase {

    override fun invoke(url: String) { externalNavigator.followTheLink(url) }
}