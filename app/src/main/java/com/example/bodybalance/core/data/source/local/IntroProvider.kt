package com.example.bodybalance.core.data.source.local

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipInputStream

public class IntroProvider(private val context: Context) {

    private val videoFile = File(context.filesDir, "intro_video.mp4")

    public fun getVideoUri(): Uri? {
        return if (videoFile.exists()) {
            Uri.fromFile(videoFile)
        } else {
            null
        }
    }

    public fun unpackVideoIfNeeded(): Boolean {
        // Проверяем, нужно ли распаковывать (флаг + существует ли файл)
        if (videoFile.exists()) {
            return true
        }

        return try {
            // Открываем файл напрямую из assets
            context.assets.open("intro_video.mp4").use { inputStream ->
                FileOutputStream(videoFile).use { outputStream ->
                    // Копируем содержимое
                    inputStream.copyTo(outputStream)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    public fun deleteVideo() {
        videoFile.delete()
    }
}