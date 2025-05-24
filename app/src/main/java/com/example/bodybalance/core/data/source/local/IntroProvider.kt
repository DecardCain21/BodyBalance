package com.example.bodybalance.core.data.source.local

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipInputStream

public class IntroProvider(private val context: Context) {

    private val sharedPrefs = context.getSharedPreferences("video_prefs", Context.MODE_PRIVATE)
    private val videoFile = File(context.filesDir, "intro_video.mp4")

    public fun getVideoUri(): Uri? {
        return if (videoFile.exists()) {
            Uri.fromFile(videoFile)
        } else {
            null
        }
    }

    public fun unpackVideoIfNeeded(): Boolean {
        if (sharedPrefs.getBoolean("is_unpacked", false) && videoFile.exists()) {
            return true
        }

        return try {
            context.assets.open("intro_video.zip").use { assetStream ->
                ZipInputStream(assetStream).use { zipStream ->
                    zipStream.nextEntry?.let { entry ->
                        if (!entry.isDirectory) {
                            FileOutputStream(videoFile).use { output ->
                                zipStream.copyTo(output)
                            }
                            sharedPrefs.edit().putBoolean("is_unpacked", true).apply()
                            true
                        } else false
                    } ?: false
                }
            }
        } catch (e: Exception) {
            false
        }
    }

    public fun deleteVideo() {
        videoFile.delete()
        sharedPrefs.edit().putBoolean("is_unpacked", false).apply()
    }
}