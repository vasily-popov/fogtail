package com.vascome.fogtail.di.appmodules;

import android.support.annotation.NonNull;

import com.vascome.fogtail.data.network.OkHttpInterceptors;
import com.vascome.fogtail.data.network.OkHttpNetworkInterceptors;

import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;

import static java.util.Collections.emptyList;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
public class OkHttpInterceptorsModule {

    @Provides @OkHttpInterceptors
    @Singleton @NonNull
    public List<Interceptor> provideOkHttpInterceptors() {
        return emptyList();
    }

    @Provides @OkHttpNetworkInterceptors
    @Singleton @NonNull
    public List<Interceptor> provideOkHttpNetworkInterceptors() {
        return emptyList();
    }
}
