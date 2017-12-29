package com.vascome.fogtail.di

import android.app.Application
import android.content.Context

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
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.di.presentation.devsettings.DevSettingsComponent
import com.vascome.fogtail.di.presentation.main.CollectionComponent





/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */


@Singleton
@Component(modules =
    [(ApplicationModule::class),
    (NetworkModule::class),
    (OkHttpInterceptorsModule::class),
    (ApiModule::class),
    (ThreadModule::class),
    (AnalyticsModule::class),
    (DeveloperSettingsModule::class)])

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: FogtailApplication)

    //submodules
    fun collectionComponent(): CollectionComponent
    fun devSettingsComponent(): DevSettingsComponent
}