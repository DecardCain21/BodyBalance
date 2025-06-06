package com.example.bodybalance.category.domain.usecase.impl

import com.example.bodybalance.category.domain.usecase.GetAllDownloadedFilesUseCase
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.util.api.FileDownloader
import javax.inject.Inject

public class GetAllDownloadedFilesUseCaseImpl @Inject constructor(
    private val fileDownloaderImpl: FileDownloader
) : GetAllDownloadedFilesUseCase {
    override fun invoke(): List<Video> {
        return fileDownloaderImpl.getAllDownloadedVideos()
    }
}