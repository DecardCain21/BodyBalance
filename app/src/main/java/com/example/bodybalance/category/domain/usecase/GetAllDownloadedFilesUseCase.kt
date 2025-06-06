package com.example.bodybalance.category.domain.usecase

import com.example.bodybalance.core.domain.models.Video

public interface GetAllDownloadedFilesUseCase {
    public operator fun invoke(): List<Video>
}