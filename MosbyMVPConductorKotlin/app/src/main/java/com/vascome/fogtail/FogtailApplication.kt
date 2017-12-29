package com.vascome.fogtail

import android.app.Application
import android.content.Context
import com.vascome.fogtail.di.AppComponent
import com.vascome.fogtail.di.DaggerAppComponent
import com.vascome.fogtail.presentation.devsettings.DeveloperSettingsModel
import com.vascome.fogtail.utils.AnalyticsModel

import javax.inject.Inject
import timber.log.Timber






/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

open class FogtailApplication : Application() {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var analyticsModel: AnalyticsModel

    @Inject
    lateinit var developerSettingModel: DeveloperSettingsModel


    override fun onCreate() {
        super.onCreate()

        appComponent =  DaggerAppComponent
                .builder()
                .application(this)
                .build()

        appComponent.inject(this)

        analyticsModel.init()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            developerSettingModel.apply()
        }
    }

    fun appComponent(): AppComponent {
        return appComponent
    }

    companion object {
        fun get(context: Context): FogtailApplication {
            return context.applicationContext as FogtailApplication
        }
    }
}
