package com.vascome.fogtail.di

import android.app.Application

import com.vascome.fogtail.FogtailApplication
import com.vascome.fogtail.di.appmodules.AnalyticsModule
import com.vascome.fogtail.di.appmodules.ApiModule
import com.vascome.fogtail.di.appmodules.ApplicationModule
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule
import com.vascome.fogtail.di.appmodules.NetworkModule
import com.vascome.fogtail.di.appmodules.OkHttpInterceptorsModule
import com.vascome.fogtail.di.appmodules.ThreadModule

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */


@Singleton
@Component(modules =
    [(ApplicationModule::class),
    (ActivityBindingModule::class),
    (NetworkModule::class),
    (OkHttpInterceptorsModule::class),
    (ApiModule::class),
    (ThreadModule::class),
    (AnalyticsModule::class),
    (DeveloperSettingsModule::class),
    (AndroidSupportInjectionModule::class)])

interface AppComponent : AndroidInjector<FogtailApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

}