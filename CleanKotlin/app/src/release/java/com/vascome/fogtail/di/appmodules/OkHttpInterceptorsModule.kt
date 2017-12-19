package com.vascome.fogtail.di.appmodules

import com.vascome.fogtail.data.network.OkHttpInterceptors
import com.vascome.fogtail.data.network.OkHttpNetworkInterceptors

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor

import java.util.Collections.emptyList

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
class OkHttpInterceptorsModule {

    @Provides
    @OkHttpInterceptors
    @Singleton
    fun provideOkHttpInterceptors(): List<Interceptor> {
        return emptyList()
    }

    @Provides
    @OkHttpNetworkInterceptors
    @Singleton
    fun provideOkHttpNetworkInterceptors(): List<Interceptor> {
        return emptyList()
    }
}
