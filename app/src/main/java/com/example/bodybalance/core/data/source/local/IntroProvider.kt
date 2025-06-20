package com.example.bodybalance.core.data.source.local

import android.content.Context
import android.net.Uri
import com.example.bodybalance.core.domain.models.Video
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File

public class IntroProvider(
    @ApplicationContext private val context: Context
) {

    private val videoFile = File(context.filesDir, "intro_video.mp4")

    public fun getVideo(): Video {
        val url = if (videoFile.exists()) {
            Uri.fromFile(videoFile).toString()
        } else {
            ""
        }

        return Video.emptyVideo(1).copy(
            localVideoUrl = url,
            category = "Введение",
            description = """
                            Это видео поможет вам быстро разобраться, как всё работает 
                            После просмотра введите кодовое слово из видео, чтобы продолжить
                        """.trimIndent(),
            name = "Введение"
        )
    }
}