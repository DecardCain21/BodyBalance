package com.example.bodybalance.settings.domain.usecase

public interface GetFileCacheSizeUseCase {
    public operator fun invoke(fileName: String): Long
}