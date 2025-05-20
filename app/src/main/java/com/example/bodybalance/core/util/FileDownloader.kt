package com.example.bodybalance.core.util

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.example.bodybalance.videoplayer.presentation.VideoPlayerViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileDownloader(private val context: Context) {

    private val client = OkHttpClient()

    public fun downloadFile(url: String, fileName: String , callback: DownloadCallback) {
        val request = Request.Builder().url(url).build()

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

    fun fileExists(fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return file.exists()
    }

    fun deleteFile(fileName: String): Boolean {
        val file = File(context.filesDir, fileName)
        return if (file.exists()) {
            file.delete()
        } else {
            false
        }
    }
}

interface DownloadCallback {
    fun onSuccess(fileDownload:Boolean)
    fun onError(error: String)
}