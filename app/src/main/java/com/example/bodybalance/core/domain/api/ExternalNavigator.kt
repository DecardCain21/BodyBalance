package com.example.bodybalance.core.domain.api

interface ExternalNavigator {

    fun followTheLink(url: String)

    fun share(text: String, title: String)
}