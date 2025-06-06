package com.example.bodybalance.core.util.api

import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.util.DownloadCallback

public interface FileDownloader {

    public fun downloadFile(video: Video, callback: DownloadCallback)

    public fun fileExists(fileName: String): Boolean

    public fun deleteFile(fileName: String): Boolean

    public fun deleteAllDownloadedFiles(): Boolean

    public fun getFilesCacheSize(): Long

    public fun getFilePathIfExists(fileName: String): String?

    public fun getAllDownloadedVideos(): List<Video>
}