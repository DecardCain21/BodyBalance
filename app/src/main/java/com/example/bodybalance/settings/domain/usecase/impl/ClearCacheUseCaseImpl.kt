package com.example.bodybalance.settings.domain.usecase.impl

import com.example.bodybalance.core.util.api.FileDownloader
import com.example.bodybalance.settings.domain.usecase.ClearCacheUseCase
import javax.inject.Inject

internal class ClearCacheUseCaseImpl @Inject constructor(
    private val fileDownloaderImpl: FileDownloader
) : ClearCacheUseCase {

    override fun invoke() {
        fileDownloaderImpl.deleteAllDownloadedFiles()
    }
}