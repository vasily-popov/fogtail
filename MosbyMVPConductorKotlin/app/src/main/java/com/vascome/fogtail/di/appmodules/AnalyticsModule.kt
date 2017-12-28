package com.vascome.fogtail.di.appmodules

import android.app.Application
import android.os.Bundle

import com.google.firebase.analytics.FirebaseAnalytics
import com.vascome.fogtail.utils.AnalyticsModel

import javax.inject.Inject
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */
@Module
class AnalyticsModule {

    @Provides
    @Singleton
    fun provideAnalyticsModel(app: Application): AnalyticsModel {
        return GoogleFirebaseAppAnalytics(app)
    }

    internal class GoogleFirebaseAppAnalytics @Inject
    constructor(private val app: Application) : AnalyticsModel {
        private var mFirebaseAnalytics: FirebaseAnalytics? = null

        override fun init() {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.app)
        }

        override fun sendEvent(eventName: String) {
            val bundle = Bundle()
            bundle.putString("name", eventName)
            mFirebaseAnalytics!!.logEvent("event", bundle)
        }

        override fun sendError(message: String, error: Throwable) {
            val bundle = Bundle()
            bundle.putString("name", message)
            bundle.putSerializable("error", error)
            mFirebaseAnalytics!!.logEvent("error_event", bundle)
        }

    }
/*
    internal class FlurryAppAnalytics(private val app: Application) : AnalyticsModel {

        override fun init() {
            FlurryAgent.Builder()
                    .withLogEnabled(true)
                    .withCaptureUncaughtExceptions(true)
                    .withContinueSessionMillis(10)
                    .build(this.app, "RGGP7ZQ2QYTTNDQWPBPP")
        }

        override fun sendEvent(eventName: String) {

            FlurryAgent.logEvent(eventName)
        }

        override fun sendError(message: String, error: Throwable) {
            FlurryAgent.onError("error", message, error)
        }
    }
*/

}
