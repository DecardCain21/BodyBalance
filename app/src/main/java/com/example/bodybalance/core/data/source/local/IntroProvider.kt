package com.example.bodybalance.core.data.source.local

import android.content.Context
import android.net.Uri
import com.example.bodybalance.core.domain.models.Video
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File

public class IntroProvider(
    @ApplicationContext private val context: Context
) {

    private val videoFileName = "intro_video.mp4"
    private val videoFile = File(context.filesDir, videoFileName)

    public fun getVideo(): Video {
        ensureVideoExists()

        val url = Uri.fromFile(videoFile).toString()

        return Video.emptyVideo(1).copy(
            localVideoUrl = url,
            category = "Введение",
            description = """
                Это видео поможет вам быстро разобраться, как всё работает. 
                После просмотра введите кодовое слово из видео, чтобы продолжить.
            """.trimIndent(),
            name = "Введение"
        )
    }

    private fun ensureVideoExists() {
        if (!videoFile.exists()) {
            context.assets.open(videoFileName).use { input ->
                videoFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
}