package com.vascome.fogtail.di.appmodules;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.vascome.fogtail.models.AppImageLoader;
import com.vascome.fogtail.models.PicassoImageLoader;
import com.vascome.fogtail.network.EntityTypeAdapterFactory;
import com.vascome.fogtail.network.OkHttpInterceptors;
import com.vascome.fogtail.network.OkHttpNetworkInterceptors;

import java.io.File;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
public class NetworkModule {

    private static final long DISK_CACHE_SIZE = 50*1024*1024; //50Mb

    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient(@NonNull Application app,
                                            @OkHttpInterceptors @NonNull List<Interceptor> interceptors,
                                            @OkHttpNetworkInterceptors @NonNull List<Interceptor> networkInterceptors) {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        for (Interceptor interceptor : interceptors) {
            okHttpBuilder.addInterceptor(interceptor);
        }

        for (Interceptor networkInterceptor : networkInterceptors) {
            okHttpBuilder.addNetworkInterceptor(networkInterceptor);
        }

        File cacheDir = new File(app.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        okHttpBuilder.cache(cache);
        return okHttpBuilder.build();
    }
}