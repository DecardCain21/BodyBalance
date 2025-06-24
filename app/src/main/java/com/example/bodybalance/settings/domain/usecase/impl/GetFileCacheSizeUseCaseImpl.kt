package com.example.bodybalance.settings.domain.usecase.impl

import com.example.bodybalance.core.util.api.FileDownloader
import com.example.bodybalance.settings.domain.usecase.GetFileCacheSizeUseCase
import javax.inject.Inject

public class GetFileCacheSizeUseCaseImpl @Inject constructor(
    private val fileDownloaderImpl: FileDownloader
) : GetFileCacheSizeUseCase {
    override fun invoke(fileName: String): Long {
        return fileDownloaderImpl.getFileSizeOrDefault(fileName = fileName)
    }
}