package com.vascome.fogtail

import com.vascome.fogtail.di.AppComponent
import com.vascome.fogtail.di.DaggerAppComponent
import com.vascome.fogtail.presentation.dev_settings.DeveloperSettingsModel
import com.vascome.fogtail.utils.AnalyticsModel
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

@Suppress("unused")
open class FogtailApplication : DaggerApplication() {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var analyticsModel: AnalyticsModel

    @Inject
    lateinit var developerSettingModel: DeveloperSettingsModel

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerAppComponent.builder().application(this).build()
        return appComponent
    }


    override fun onCreate() {
        super.onCreate()

        analyticsModel.init()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            developerSettingModel.apply()
        }
    }

    fun appComponent(): AppComponent {
        return appComponent
    }
}