package com.example.bodybalance.core.util

import android.content.Context
import com.example.bodybalance.core.domain.api.SettingsToolsRepository
import com.example.bodybalance.core.util.api.FileDownloader
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class FileDownloaderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsToolsRepository: SettingsToolsRepository
) : FileDownloader {

    private val client = OkHttpClient()

    override fun downloadFile(url: String, fileName: String, callback: DownloadCallback) {
        val request = Request.Builder().url(url).build()
        val downloadOnlyWifi = settingsToolsRepository.getDownloadWifiFlag()
        if (downloadOnlyWifi && !isConnectedToWifi()) {
            callback.onError("Нет активного соединения с Wi-Fi")
            return
        }
        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Обработка ошибки
                e.printStackTrace()
                callback.onError(e.message ?: "Network error")
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (!response.isSuccessful) {
                    callback.onError("HTTP error: ${response.code}")
                    return
                }

                try {
                    val file = File(context.filesDir, fileName)
                    response.body?.byteStream()?.use { input ->
                        FileOutputStream(file).use { output ->
                            input.copyTo(output)
                        }
                    }
                    callback.onSuccess(true)
                } catch (e: Exception) {
                    callback.onError("File save error: ${e.message}")
                }
            }
        })
    }

    override fun fileExists(fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists()
    }

    override fun deleteFile(fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }

    override fun deleteAllDownloadedFiles(): Boolean {
        val filesDir = context.filesDir
        val files = filesDir.listFiles()

        if (files == null || files.isEmpty()) {
            return false // Нет файлов для удаления
        }

        var allDeleted = true
        for (file in files) {
            if (!file.delete()) {
                allDeleted = false
            }
        }

        return allDeleted
    }

    override fun getFilesCacheSize(): Long {
        val files = context.filesDir.listFiles() ?: return 0L

        return files.sumOf { it.length() }
    }

}

interface DownloadCallback {
    fun onSuccess(fileDownload: Boolean)
    fun onError(error: String)
}