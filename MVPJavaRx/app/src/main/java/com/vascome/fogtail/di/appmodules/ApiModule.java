package com.vascome.fogtail.di.appmodules;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.vascome.fogtail.BuildConfig;
import com.vascome.fogtail.api.ApiConfiguration;
import com.vascome.fogtail.api.FogtailRestApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
public class ApiModule {

    @Provides @NonNull @Singleton
    public FogtailRestApi provideRestApi(@NonNull OkHttpClient okHttpClient,
                                         @NonNull Gson gson,
                                         @NonNull ApiConfiguration config) {
        return new Retrofit.Builder()
                .baseUrl(config.getBaseApiUrl())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)  // Fail early: check Retrofit configuration at creation time in Debug build.
                .build()
                .create(FogtailRestApi.class);
    }

    @Provides @NonNull
    public ApiConfiguration provideConfiguration() {
        return () -> "https://raw.githubusercontent.com/vascome/fogtail/master/MVPJava/app/src/main/assets/";
    }
}