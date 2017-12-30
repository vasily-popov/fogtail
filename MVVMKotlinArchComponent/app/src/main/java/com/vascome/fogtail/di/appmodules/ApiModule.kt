package com.vascome.fogtail.di.appmodules

import com.squareup.moshi.Moshi
import com.vascome.fogtail.BuildConfig
import com.vascome.fogtail.data.api.ApiConfiguration
import com.vascome.fogtail.data.api.FogtailRestApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideRestApi(okHttpClient:OkHttpClient,
                       moshi:Moshi,
                       config:ApiConfiguration):FogtailRestApi {
        return Retrofit.Builder()
                .baseUrl(config.baseApiUrl)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)  // Fail early: check Retrofit configuration at creation time in Debug build.
                .build()
                .create<FogtailRestApi>(FogtailRestApi::class.java)
    }

    @Provides
    fun provideConfiguration():ApiConfiguration {

        return object : ApiConfiguration {
            override val baseApiUrl: String
                get() = "https://raw.githubusercontent.com/vascome/fogtail/master/MVPJava/app/src/main/assets/"
        }
    }
}