package com.vascome.fogtail.di

import com.google.gson.Gson
import com.vascome.fogtail.BuildConfig
import com.vascome.fogtail.data.api.FogtailRestApi

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by vasilypopov on 12/14/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */

@Module
class FunctionalTestModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpBuilder = OkHttpClient.Builder()
        return okHttpBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRestApi(okHttpClient: OkHttpClient,
                       gson: Gson,
                       server: MockWebServer): FogtailRestApi {
        return Retrofit.Builder()
                .baseUrl(server.url("/"))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)  // Fail early: check Retrofit configuration at creation time in Debug build.
                .build()
                .create(FogtailRestApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMockServer(): MockWebServer {
        return MockWebServer()
    }

}
