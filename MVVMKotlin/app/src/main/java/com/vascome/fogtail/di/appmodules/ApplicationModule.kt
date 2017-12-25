package com.vascome.fogtail.di.appmodules

import android.app.Application
import android.content.Context

import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.moshi.Moshi
import com.squareup.picasso.Picasso
import com.vascome.fogtail.data.network.AppImageLoader
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
    /*
    @Provides
    @Singleton
    fun provideTypeAdapterFactory(): JsonAdapter.Factory {
        return KotlinJsonAdapterFactory()
    }
*/
    @Provides
    @Singleton
    fun provideMoshi():Moshi {
        return Moshi.Builder()
                //.add(typeAdapterFactory)
                .build()
    }

    @Provides
    @Singleton
    fun providePicasso(application:Application, okHttpClient:OkHttpClient):Picasso {
        return Picasso.Builder(application)
                .downloader(OkHttp3Downloader(okHttpClient))
                .listener({ _, uri, e-> Timber.e(e, "Failed to load image: %s", uri) })
                .build()
    }

    @Provides
    @Singleton
    fun provideImageLoader(picasso:Picasso):AppImageLoader {
        return PicassoImageLoader(picasso)
    }

    @Provides
    @Singleton
    fun provideContext(application:Application):Context {
        return application.applicationContext
    }
}
