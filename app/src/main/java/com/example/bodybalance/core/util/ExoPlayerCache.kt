package com.example.bodybalance.core.util

import android.content.Context
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.Cache
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import java.io.File

@UnstableApi
public object ExoPlayerCache {

    private var cache: SimpleCache? = null

    private fun getCache(context: Context): Cache {
        if (cache == null) {
            val cacheDir = File(context.cacheDir, "video_cache")
            val size = LeastRecentlyUsedCacheEvictor(150L * 1024 * 1024) // 1000MB кеш
            cache = SimpleCache(cacheDir, size)
        }
        return cache!!
    }

    public fun getCacheDataSourceFactory(context: Context): CacheDataSource.Factory {
        val httpDataSourceFactory = DefaultHttpDataSource.Factory()
        val defaultDataSourceFactory = DefaultDataSource.Factory(context, httpDataSourceFactory)
        return CacheDataSource.Factory()
            .setCache(getCache(context))
            .setUpstreamDataSourceFactory(defaultDataSourceFactory)

    }
}