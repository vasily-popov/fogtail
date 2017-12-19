package com.vascome.fogtail.presentation.devsettings.model

import android.app.Application
import android.util.DisplayMetrics

import com.codemonkeylabs.fpslibrary.TinyDancer
import com.facebook.stetho.Stetho
import com.vascome.fogtail.BuildConfig
import com.vascome.fogtail.presentation.devsettings.DeveloperSettingsModel
import com.vascome.fogtail.utils.LeakCanaryProxy

import java.util.concurrent.atomic.AtomicBoolean

import javax.inject.Inject

import hu.supercluster.paperwork.Paperwork
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

import android.view.Gravity.START
import android.view.Gravity.TOP

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class DeveloperSettingsModelImpl
@Inject constructor(private val application: Application,
            private val developerSettings: DeveloperSettings,
            private val httpLoggingInterceptor: HttpLoggingInterceptor,
            private val leakCanaryProxy: LeakCanaryProxy,
            private val paperwork: Paperwork) : DeveloperSettingsModel {

    private val stethoAlreadyEnabled = AtomicBoolean()

    private val leakCanaryAlreadyEnabled = AtomicBoolean()

    private val tinyDancerDisplayed = AtomicBoolean()

    val gitSha: String
        get() = paperwork.get("gitSha")

    val buildDate: String
        get() = paperwork.get("buildDate")

    val buildVersionCode: String
        get() = BuildConfig.VERSION_CODE.toString()

    val buildVersionName: String
        get() = BuildConfig.VERSION_NAME

    val isStethoEnabled: Boolean
        get() = developerSettings.isStethoEnabled

    val isLeakCanaryEnabled: Boolean
        get() = developerSettings.isLeakCanaryEnabled

    val isTinyDancerEnabled: Boolean
        get() = developerSettings.isTinyDancerEnabled

    val httpLoggingLevel: HttpLoggingInterceptor.Level
        get() = developerSettings.httpLoggingLevel

    fun changeStethoState(enabled: Boolean) {
        developerSettings.saveIsStethoEnabled(enabled)
        apply()
    }

    fun changeLeakCanaryState(enabled: Boolean) {
        developerSettings.saveIsLeakCanaryEnabled(enabled)
        apply()
    }

    fun changeTinyDancerState(enabled: Boolean) {
        developerSettings.saveIsTinyDancerEnabled(enabled)
        apply()
    }

    fun changeHttpLoggingLevel(loggingLevel: HttpLoggingInterceptor.Level) {
        developerSettings.saveHttpLoggingLevel(loggingLevel)
        apply()
    }

    override fun apply() {
        // Stetho can not be enabled twice.
        if (stethoAlreadyEnabled.compareAndSet(false, true)) {
            if (isStethoEnabled) {
                Stetho.initializeWithDefaults(application)
            }
        }

        // LeakCanary can not be enabled twice.
        if (leakCanaryAlreadyEnabled.compareAndSet(false, true)) {
            if (isLeakCanaryEnabled) {
                leakCanaryProxy.init()
            }
        }

        if (isTinyDancerEnabled && tinyDancerDisplayed.compareAndSet(false, true)) {
            val displayMetrics = application.resources.displayMetrics

            TinyDancer.create()
                    .redFlagPercentage(0.2f)
                    .yellowFlagPercentage(0.05f)
                    .startingGravity(TOP or START)
                    .startingXPosition(displayMetrics.widthPixels / 10)
                    .startingYPosition(displayMetrics.heightPixels / 4)
                    .show(application)
        } else if (tinyDancerDisplayed.compareAndSet(true, false)) {
            try {
                TinyDancer.hide(application)
            } catch (e: Exception) {
                // In some cases TinyDancer cannot be hidden without an exception: for example, when you start it for the first time on Android 6.
                Timber.e(e, "Can not hide TinyDancer")
            }

        }

        httpLoggingInterceptor.level = developerSettings.httpLoggingLevel
    }

}
