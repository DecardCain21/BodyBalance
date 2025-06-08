package com.example.bodybalance.settings.domain.usecase.impl

import com.example.bodybalance.core.domain.api.SavedVideoRepository
import com.example.bodybalance.core.util.api.FileDownloader
import com.example.bodybalance.settings.domain.usecase.ClearCacheUseCase
import javax.inject.Inject

public class ClearCacheUseCaseImpl @Inject constructor(
    private val fileDownloaderImpl: FileDownloader,
    private val savedVideoRepository: SavedVideoRepository
) : ClearCacheUseCase {

    override suspend fun invoke() {
        fileDownloaderImpl.deleteAllDownloadedFiles()
        savedVideoRepository.deleteAllSavedVideo()
    }
}