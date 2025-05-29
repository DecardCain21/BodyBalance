package com.example.bodybalance.core.util.api

import com.example.bodybalance.core.util.DownloadCallback

interface FileDownloader {
    fun downloadFile(url: String, fileName: String, callback: DownloadCallback)

    fun fileExists(fileName: String): Boolean

    fun deleteFile(fileName: String): Boolean

    fun deleteAllDownloadedFiles(): Boolean

    fun getFilesCacheSize(): Long
}