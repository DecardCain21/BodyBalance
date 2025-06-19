package com.example.bodybalance.core.util

import android.content.Context
import com.example.bodybalance.core.domain.api.SettingsToolsRepository
import com.example.bodybalance.core.domain.models.Video
import com.example.bodybalance.core.domain.usecase.api.SaveVideoInCacheUseCase
import com.example.bodybalance.core.util.api.FileDownloader
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Collections
import javax.inject.Inject

public class FileDownloaderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsToolsRepository: SettingsToolsRepository,
    private val okHttpClient: OkHttpClient,
    private val saveVideoInCacheUseCase: SaveVideoInCacheUseCase,
) : FileDownloader {

    private val excluded = setOf("intro_video.mp4", "profileInstalled")

    private val activeDownloads = Collections.synchronizedMap(mutableMapOf<Int, Call>())
    private val _activeDownloadsFlow = MutableStateFlow<Set<Int>>(emptySet())
    public override val activeDownloadsFlow: StateFlow<Set<Int>> get() = _activeDownloadsFlow

    private fun addDownload(id: Int, call: Call) {
        synchronized(activeDownloads) {
            activeDownloads[id] = call
            _activeDownloadsFlow.value = activeDownloads.keys.toSet()
        }
    }

    private fun removeDownload(id: Int) {
        synchronized(activeDownloads) {
            activeDownloads.remove(id)
            _activeDownloadsFlow.value = activeDownloads.keys.toSet()
        }
    }

    override fun downloadFile(video: Video, callback: DownloadCallback) {
        val request = Request.Builder().url(video.remoteVideoUrl).build()
        val downloadOnlyWifi = settingsToolsRepository.getDownloadWifiFlag()
        if (downloadOnlyWifi && !isConnectedToWifi()) {
            callback.onError(FileDownloaderError.WIFI_ERROR)
            return
        }
        val call = okHttpClient.newCall(request)
        addDownload(video.id, call)

        call.enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                removeDownload(video.id)
                if (e.message == "Canceled") {
                    callback.onError(FileDownloaderError.DOWNLOAD_CANCEL)
                } else {
                    callback.onError(FileDownloaderError.NETWORK_ERROR)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    removeDownload(video.id)
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
                        saveVideoInCacheUseCase(video.copy(localVideoUrl = filePath))
                    }
                    removeDownload(video.id)
                    callback.onSuccess()
                } catch (e: java.net.SocketException) { // Потеря интернета
                    deleteFile(video.id.toString())
                    removeDownload(video.id)
                    callback.onError(FileDownloaderError.NETWORK_ERROR)
                } catch (e: okhttp3.internal.http2.StreamResetException) { // Отмена скачивания
                    deleteFile(video.id.toString())
                    removeDownload(video.id)
                    callback.onError(FileDownloaderError.DOWNLOAD_CANCEL)
                } catch (e: Exception) {
                    deleteFile(video.id.toString())
                    removeDownload(video.id)
                    callback.onError(FileDownloaderError.FILE_SAVE_ERROR)
                }
            }
        })
    }

    override fun cancelDownload(videoId: Int) {
        deleteFile(videoId.toString())
        activeDownloads[videoId]?.cancel()
        removeDownload(videoId)
    }

    override fun checkDownloadingProcess(videoId: Int): Boolean {
        return activeDownloads.containsKey(key = videoId)
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
            if (!excluded.contains(file.name) && !file.delete()) {
                allDeleted = false
            }
        }

        return allDeleted
    }

    override fun getFilesCacheSize(): Long {
        return context.filesDir
            ?.listFiles()
            ?.filter { it.isFile && !excluded.contains(it.name) }
            ?.sumOf { it.length() }
            ?: 0L
    }

    override fun getFilePathIfExists(fileName: String): String? {
        val file = File(context.filesDir, fileName)
        return file.takeIf { it.exists() }?.absolutePath
    }
}

public interface DownloadCallback {
    public fun onSuccess()
    public fun onError(error: FileDownloaderError)
}

public enum class FileDownloaderError(public val error: String) {
    HTTP_ERROR("Ошибка сервера"),
    WIFI_ERROR("Нет активного соединения с Wi-Fi"),
    NETWORK_ERROR("Ошибка сети"),
    FILE_SAVE_ERROR("Ошибка сохранения файла"),
    DOWNLOAD_CANCEL("Загрузка видео была отменена")
}