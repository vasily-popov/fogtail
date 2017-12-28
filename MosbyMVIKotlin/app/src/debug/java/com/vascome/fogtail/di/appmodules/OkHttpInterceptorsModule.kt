package com.vascome.fogtail.di.appmodules

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.vascome.fogtail.data.network.OkHttpInterceptors
import com.vascome.fogtail.data.network.OkHttpNetworkInterceptors
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import javax.inject.Singleton

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
class OkHttpInterceptorsModule {

    // Provided as separate dependency for Developer Settings to be able to change HTTP log level at runtime.
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message -> Timber.d(message) }
    }

    @Provides
    @OkHttpInterceptors
    @Singleton
    fun provideOkHttpInterceptors(httpLoggingInterceptor: HttpLoggingInterceptor): List<@JvmSuppressWildcards Interceptor> {
        return listOf<Interceptor>(httpLoggingInterceptor)
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    fun provideOkHttpNetworkInterceptors(): List<@JvmSuppressWildcards Interceptor> {
        return listOf<Interceptor>(StethoInterceptor())
    }
}
