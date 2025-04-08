package com.example.bodybalance.core.data.service

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.core.domain.api.ExternalNavigator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@OptIn(UnstableApi::class)
class ExternalNavigatorImpl @Inject constructor(
    @ApplicationContext private val context: Context
): ExternalNavigator {

    override fun followTheLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e("ExternalNavigator", "No activity found to handle URL: $url", e)
            // Toast.makeText(context, "Невозможно открыть ссылку", Toast.LENGTH_SHORT).show()
        }
    }

    override fun share(text: String, title: String) {
        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, text)

        val chooserIntent = Intent.createChooser(intent, title)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            context.startActivity(chooserIntent)
        } catch (e: ActivityNotFoundException) {
            Log.e("ExternalNavigator", "No app found to share text", e)
            //Toast.makeText(context, "Невозможно поделиться", Toast.LENGTH_SHORT).show()
        }
    }
}