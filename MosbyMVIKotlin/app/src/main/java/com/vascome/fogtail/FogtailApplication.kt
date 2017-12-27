package com.vascome.fogtail

import android.app.Activity
import android.app.Application
import com.vascome.fogtail.di.AppComponent
import com.vascome.fogtail.di.DaggerAppComponent
import com.vascome.fogtail.di.applyAutoInjector
import com.vascome.fogtail.presentation.devsettings.DeveloperSettingsModel
import com.vascome.fogtail.utils.AnalyticsModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

open class FogtailApplication : Application(), HasActivityInjector {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var analyticsModel: AnalyticsModel

    @Inject
    lateinit var developerSettingModel: DeveloperSettingsModel

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()

        appComponent =  DaggerAppComponent
                .builder()
                .application(this)
                .build()
        appComponent.inject(this)

        applyAutoInjector()

        analyticsModel.init()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            developerSettingModel.apply()
        }
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }


    @Suppress("unused")
    fun appComponent(): AppComponent {
        return appComponent
    }
}