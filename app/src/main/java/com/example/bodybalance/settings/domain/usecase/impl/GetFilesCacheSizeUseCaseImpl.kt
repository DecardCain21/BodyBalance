package com.example.bodybalance.settings.domain.usecase.impl

import com.example.bodybalance.core.util.api.FileDownloader
import com.example.bodybalance.settings.domain.usecase.GetFilesCacheSizeUseCase
import javax.inject.Inject

public class GetFilesCacheSizeUseCaseImpl @Inject constructor(
    private val fileDownloaderImpl: FileDownloader
): GetFilesCacheSizeUseCase {

    override fun invoke(): Long {
        return fileDownloaderImpl.getFilesCacheSize()
    }
}