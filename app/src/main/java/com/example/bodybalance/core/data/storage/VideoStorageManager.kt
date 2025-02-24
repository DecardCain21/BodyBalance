package com.example.bodybalance.core.data.storage

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.media3.common.util.UnstableApi
import com.example.bodybalance.app.BodyBalanceApp
import com.example.bodybalance.core.longcache.VideoDownload

@UnstableApi
class VideoStorageManager {
    //Реализовать хранилище
    //Реализовать запрос
    //Test

    private var mPersistence: MutableList<VideoDownload> = mutableListOf(VideoDownload(123, 123))

    private val mDownloads: MutableList<VideoDownload> by lazy {
        mPersistence ?: mutableListOf()
    }

    private val downloadManager by lazy {
        BodyBalanceApp.applicationContext()
            .getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    }


    fun startDownload() {
        val videoId: Long = 1
        val description: String = ""
        val zipUrl: String = ""
        val isOnlyWifi: Boolean = true
        //
        val downloadRequest = DownloadManager.Request(Uri.parse(zipUrl))
            .setAllowedOverMetered(isOnlyWifi)
            .setTitle(description)
            .setNotificationVisibility(
                DownloadManager.Request.VISIBILITY_VISIBLE
            )
            .setVisibleInDownloadsUi(false) // deprecated в Android Q !!
            .addRequestHeader("Authorization", "Bearer" + 123/*sessionToken*/)
        //
        val downloadId = downloadManager.enqueue(downloadRequest)
        val model = VideoDownload(downloadId, videoId)
        mDownloads.add(model)
        mPersistence = mDownloads

    }

    fun getDMStatus(downloadId: Long): Int? {
        val request = DownloadManager.Query().setFilterById(downloadId)
        downloadManager.query(request).use {
            return if (it.count > 0) {
                it.getColumnIndex(DownloadManager.COLUMN_STATUS)
            } else null
        }
    }

    fun unzip(model: VideoDownload) {
        val pfd: ParcelFileDescriptor = downloadManager.openDownloadedFile(model.downloadId)
        val fd = pfd.fileDescriptor

        /*removeDownload(model)*/
    }

    //Presentation
    //
    /*fun pickDownloadState() {
        if (hasLocalCope(videoId)) {
            viewState.showPlayButton()
        } else {
            val model = getDownload(videoId)
            val status = getDMStatus(model.downloadId)
            when (status) {
                null,
                DownloadManager.STATUS_FAILED -> viewState.showDLButton()

                DownloadManager.STATUS_PENDING,
                DownloadManager.STATUS_RUNNING,
                DownloadManager.STATUS_PAUSED -> viewState.showStopButton

                DownloadManager.STATUS_SUCCESSFUL -> unzip(model)

            }
        }
    }*/

    /*override fun attachView(view:VideoDetailsView?){
        super.attachView(view)
        pickDownloadState()
    }*/

    /*fun stopDownload() {
        val model = getDownload(videoId)
        model?.let {
            removeDownload(it)
        }
        viewState.showDLButton()
    }*/

    /*fun removeDownload(model: VideoDownload) {
        mDownloads.remove(model)
        mPersistence = mDownloads
        downloadManager.remove(model.downloadId)
    }*/

    /*private val mReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val downloadId = intent?.getLongExtra(
                DownloadManager.EXTRA_DOWNLOAD_ID,
                -1L
            )
            viewModel.onDownloadComplete(downloadId)
        }
    }*/

    /*fun onDownloadComplete(downloadId: Long) {
        val model = getDownloadById(downloadId)
        if (model != null && model.videoId == videoId) {
            val status = getDMStatus(downloadId)
            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                unzip(model)
                viewState.showPlayButton()
            } else {
                removeDownload(model)
                viewState.showDLButton
            }

        }
    }*/
}