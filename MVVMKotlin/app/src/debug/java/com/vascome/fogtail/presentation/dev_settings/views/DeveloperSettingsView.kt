package com.vascome.fogtail.presentation.dev_settings.views

import android.support.annotation.AnyThread

import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

interface DeveloperSettingsView {

    @AnyThread
    fun changeGitSha(gitSha: String)

    @AnyThread
    fun changeBuildDate(date: String)

    @AnyThread
    fun changeBuildVersionCode(versionCode: String)

    @AnyThread
    fun changeBuildVersionName(versionName: String)

    @AnyThread
    fun changeStethoState(enabled: Boolean)

    @AnyThread
    fun changeLeakCanaryState(enabled: Boolean)

    @AnyThread
    fun changeTinyDancerState(enabled: Boolean)

    @AnyThread
    fun changeHttpLoggingLevel(loggingLevel: HttpLoggingInterceptor.Level)

    @AnyThread
    fun showMessage(message: String)

    @AnyThread
    fun showAppNeedsToBeRestarted()

}
