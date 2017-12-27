package com.vascome.fogtail.di

import android.app.Application

import com.vascome.fogtail.utils.AnalyticsModel

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import timber.log.Timber

/**
 * Created by vasilypopov on 12/14/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */
@Module
class FunctionalAnalyticsModule {

    @Provides
    @Singleton
    fun provideAnalyticsModel(app: Application): AnalyticsModel {
        // We don't need real analytics in Functional tests, but let's just log it instead!
        return object : AnalyticsModel {

            // We'll check that application initializes Analytics before working with it!
            @Volatile private var isInitialized: Boolean = false

            override fun init() {
                isInitialized = true
                Timber.d("Analytics: initialized.")
            }

            override fun sendEvent(eventName: String) {
                throwIfNotInitialized()
                Timber.d("Analytics: send event %s", eventName)
            }

            override fun sendError(message: String, error: Throwable) {
                throwIfNotInitialized()
                Timber.e(error, message)
            }

            private fun throwIfNotInitialized() {
                if (!isInitialized) {
                    throw AssertionError("Analytics was not initialized!")
                }
            }
        }
    }

}
