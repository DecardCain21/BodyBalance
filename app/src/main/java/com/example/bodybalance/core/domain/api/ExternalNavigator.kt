package com.example.bodybalance.core.domain.api

public interface ExternalNavigator {

    public fun followTheLink(url: String)

    public fun share(text: String, title: String)
}