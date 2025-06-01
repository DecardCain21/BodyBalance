package com.example.bodybalance.core.domain.usecase.api

public interface ShareContentUseCase {

    public  operator fun invoke(text: String, title: String)
}