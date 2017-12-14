package com.vascome.fogtail.di;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.vascome.fogtail.BuildConfig;
import com.vascome.fogtail.data.api.FogtailRestApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vasilypopov on 12/14/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */

@Module
public class FunctionalTestModule {

    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        return okHttpBuilder.build();
    }

    @Provides
    @Singleton
    public FogtailRestApi provideRestApi(@NonNull OkHttpClient okHttpClient,
                                         @NonNull Gson gson,
                                         @NonNull MockWebServer server) {
        return new Retrofit.Builder()
                .baseUrl(server.url("/"))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)  // Fail early: check Retrofit configuration at creation time in Debug build.
                .build()
                .create(FogtailRestApi.class);
    }
    @Provides
    @Singleton
    public MockWebServer provideMockServer() {
        return new MockWebServer();
    }

}
