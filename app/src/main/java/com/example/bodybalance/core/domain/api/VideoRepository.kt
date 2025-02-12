package com.example.bodybalance.core.domain.api

import com.example.bodybalance.core.domain.models.Account

interface VideoRepository {
    fun getVideo(account: Account, category: Int, prevId: Int): String
}