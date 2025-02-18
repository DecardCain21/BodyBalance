package com.example.bodybalance.core.data.repository

import com.example.bodybalance.core.data.network.VideoNetworkClient
import com.example.bodybalance.core.domain.api.VideoRepository
import com.example.bodybalance.core.domain.models.Account
import com.example.bodybalance.core.domain.models.AccountTypeTherapy
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(videoNetworkClient: VideoNetworkClient) : VideoRepository {


    override fun getVideo(account: Account, category: Int, prevId: Int): String {
        return requestServer(account, category, prevId)
    }

    private fun requestServer(account: Account, category: Int, prevId: Int): String {
        return when (account.type) {
            AccountTypeTherapy.ExerciseBasic -> "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            AccountTypeTherapy.ExercisePro -> " https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
            AccountTypeTherapy.RehabilitationFirst -> "https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4"
            AccountTypeTherapy.RehabilitationSecond -> " https://storage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
        }
    }
}