package com.example.bodybalance.core.data.service

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FileDownloader(private val context: Context) {

    private val client = OkHttpClient()

    fun downloadFile(url: String, fileName: String) {
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // Обработка ошибки
                e.printStackTrace()
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    // Получаем поток данных
                    val inputStream = response.body?.byteStream()

                    // Сохраняем файл в приватное хранилище
                    val file = File(context.filesDir, fileName)
                    val outputStream = FileOutputStream(file)

                    inputStream?.use { input ->
                        outputStream.use { output ->
                            input.copyTo(output)
                        }
                    }

                    // Файл успешно сохранен
                    println("Файл сохранен: ${file.absolutePath}")
                } else {
                    // Обработка неудачного ответа
                    println("Ошибка: ${response.code}")
                }
            }
        })
    }
}