package com.example.bodybalance.core.util.api

import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.util.DownloadCallback
import kotlinx.coroutines.flow.StateFlow

public interface FileDownloader {

    public val activeDownloadsFlow: StateFlow<Set<Int>>

    public fun downloadFile(video: Video, callback: DownloadCallback)

    public fun cancelDownload(videoId: Int)

    public fun checkDownloadingProcess(videoId: Int): Boolean

    public fun fileExists(fileName: String): Boolean

    public fun deleteFile(fileName: String): Boolean

    public fun deleteAllDownloadedFiles(): Boolean

    public fun getFilesCacheSize(): Long

    public fun getFilePathIfExists(fileName: String): String?
}