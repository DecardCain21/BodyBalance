package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.convertor.convertToCategory
import com.example.bodybalance.core.data.source.network.client.VideoNetworkClient
import com.example.bodybalance.core.domain.api.VideoRepository
import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.AccountTypeTherapy
import com.example.bodybalance.core.domain.models.Category
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val videoNetworkClient: VideoNetworkClient
) : VideoRepository {

    // account: Account,
    override suspend fun getVideo(category: String): Result<Category> {
        return videoNetworkClient.getVideo(category = category)
            .map { it.convertToCategory() }
    }

    private fun requestServer(account: Account, category: Int, prevId: Int): String {
        return when (account.type) {
            AccountTypeTherapy.ExerciseBasic -> "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            AccountTypeTherapy.ExercisePro -> "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
            AccountTypeTherapy.RehabilitationFirst -> "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
            AccountTypeTherapy.RehabilitationSecond -> " https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
        }
    }
}