package com.vascome.fogtail.di.appmodules

import android.app.Application

import com.vascome.fogtail.data.network.OkHttpInterceptors
import com.vascome.fogtail.data.network.OkHttpNetworkInterceptors

import java.io.File

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(app:Application,
                            @OkHttpInterceptors interceptors:List<@JvmSuppressWildcards Interceptor>,
                            @OkHttpNetworkInterceptors networkInterceptors:List<@JvmSuppressWildcards Interceptor>):OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()

        for (interceptor in interceptors)
        {
            okHttpBuilder.addInterceptor(interceptor)
        }

        for (networkInterceptor in networkInterceptors)
        {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor)
        }

        val cacheDir = File(app.getCacheDir(), "http")
        val cache = Cache(cacheDir, DISK_CACHE_SIZE)

        okHttpBuilder.cache(cache)
        return okHttpBuilder.build()
    }

    companion object {

        private val DISK_CACHE_SIZE = (50 * 1024 * 1024).toLong() //50Mb
    }

}