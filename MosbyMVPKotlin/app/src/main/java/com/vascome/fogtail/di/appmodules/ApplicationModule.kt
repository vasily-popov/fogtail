package com.vascome.fogtail.di.appmodules

import android.app.Application
import android.content.Context

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.data.network.EntityTypeAdapterFactory
import com.vascome.fogtail.data.network.PicassoImageLoader

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import timber.log.Timber

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */
@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideTypeAdapterFactory(): TypeAdapterFactory {
        return EntityTypeAdapterFactory.create()
    }

    @Provides
    @Singleton
    fun provideGson(typeAdapterFactory: TypeAdapterFactory): Gson {
        return GsonBuilder()
                .registerTypeAdapterFactory(typeAdapterFactory)
                .create()
    }

    @Provides
    @Singleton
    fun providePicasso(application: Application, okHttpClient: OkHttpClient): Picasso {
        return Picasso.Builder(application)
                .downloader(OkHttp3Downloader(okHttpClient))
                .listener { _, uri, e -> Timber.e(e, "Failed to load image: %s", uri) }
                .build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(picasso: Picasso): AppImageLoader {
        return PicassoImageLoader(picasso)
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

}
