package com.example.bodybalance.core.util

import android.content.Context
import com.example.bodybalance.core.domain.api.SettingsToolsRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.SaveVideoInCacheUseCase
import com.example.bodybalance.core.util.api.FileDownloader
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

public class FileDownloaderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsToolsRepository: SettingsToolsRepository,
    private val okHttpClient: OkHttpClient,
    private val saveVideoInCacheUseCase: SaveVideoInCacheUseCase,
) : FileDownloader {

    override fun downloadFile(video: Video, callback: DownloadCallback) {
        val request = Request.Builder().url(video.url).build()
        val downloadOnlyWifi = settingsToolsRepository.getDownloadWifiFlag()
        if (downloadOnlyWifi && isConnectedToWifi()) {
            callback.onError(FileDownloaderError.WIFI_ERROR)
            return
        }
        okHttpClient.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Обработка ошибки
                e.printStackTrace()
                callback.onError(FileDownloaderError.NETWORK_ERROR)
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (!response.isSuccessful) {
                    callback.onError(FileDownloaderError.HTTP_ERROR)
                    return
                }

                val test = response.headers["Content-Length"] // todo:
                try {
                    val file = File(context.filesDir, video.id.toString())
                    response.body?.byteStream()?.use { input ->
                        FileOutputStream(file).use { output ->
                            input.copyTo(output)
                        }
                    }
                    val filePath = file.absolutePath
                    CoroutineScope(Dispatchers.IO).launch {
                        saveVideoInCacheUseCase(video.copy(url = filePath))
                    }
                    callback.onSuccess(file.absolutePath)
                } catch (e: Exception) {
                    deleteFile(video.id.toString())
                    callback.onError(FileDownloaderError.FILE_SAVE_ERROR)
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

    override fun getFilePathIfExists(fileName: String): String? {
        val file = File(context.filesDir, fileName)
        return file.takeIf { it.exists() }?.absolutePath
    }

    override fun getAllDownloadedVideos(): List<Video> {
        val filesDir = context.filesDir
        val files = filesDir.listFiles() ?: return emptyList()

        return files.mapNotNull { file ->
            try {
                // Предполагаем, что имя файла - это ID видео
                val videoId = file.nameWithoutExtension.toInt()

                Video(
                    id = videoId,
                    name = "Видео $videoId", // Можно заменить на реальное имя из БД
                    url = file.absolutePath, // Локальный путь к файлу
                    category = "Скачанные", // Категория для скачанных видео
                    description = "Скачанное видео ${file.name}",
                    imageUrl = "" // Можно добавить путь к превью, если есть
                )
            } catch (e: NumberFormatException) {
                // Пропускаем файлы, которые не могут быть преобразованы в ID видео
                null
            }
        }
    }

}

public interface DownloadCallback {
    public fun onSuccess(url: String)
    public fun onError(error: FileDownloaderError)
}

public enum class FileDownloaderError(public val error: String) {
    HTTP_ERROR("Ошибка сервера"),
    WIFI_ERROR("Нет активного соединения с Wi-Fi"),
    NETWORK_ERROR("Ошибка сети"),
    FILE_SAVE_ERROR("Ошибка сохранения файла")
}