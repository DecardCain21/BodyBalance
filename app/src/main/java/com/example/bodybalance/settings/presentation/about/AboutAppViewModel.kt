package com.example.bodybalance.settings.presentation.about

import androidx.lifecycle.ViewModel
import com.example.bodybalance.core.domain.usecase.api.FollowLinkUseCase
import com.example.bodybalance.core.domain.usecase.api.ShareContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class AboutAppViewModel @Inject constructor(
    private val followLinkUseCase: FollowLinkUseCase,
    private val shareContentUseCase: ShareContentUseCase
) : ViewModel() {

    fun handleEvent(event: AboutAppScreenUiEvent) {
        when (event) {
            is AboutAppScreenUiEvent.OpenContributorLink -> openContributorLink(event.url)
            is AboutAppScreenUiEvent.ShareTheApp -> shareTheApp(
                text = event.text,
                title = event.title
            )
        }
    }

    private fun shareTheApp(text: String, title: String) {
        shareContentUseCase(text = text, title = title)
    }

    private fun openContributorLink(url: String) {
        followLinkUseCase(url)
    }
}