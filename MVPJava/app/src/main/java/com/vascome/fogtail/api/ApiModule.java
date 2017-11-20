package com.vascome.fogtail.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.vascome.fogtail.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
public class ApiModule {

    @NonNull
    private String baseUrl;

    public ApiModule(@NonNull  String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides @NonNull @Singleton
    public FogtailRestApi provideRestApi(@NonNull OkHttpClient okHttpClient, @NonNull Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)  // Fail early: check Retrofit configuration at creation time in Debug build.
                .build()
                .create(FogtailRestApi.class);
    }
}