package com.vascome.fogtail.presentation.devsettings.views

import android.support.annotation.AnyThread
import android.support.annotation.UiThread
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

interface DeveloperSettingsContract {

    interface View: MvpView {

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

    interface Presenter: MvpPresenter<View> {
        @AnyThread
        fun syncDeveloperSettings()
        @AnyThread
        fun changeStethoState(enabled: Boolean)
        @AnyThread
        fun changeLeakCanaryState(enabled: Boolean)
        @AnyThread
        fun changeTinyDancerState(enabled: Boolean)
        @AnyThread
        fun changeHttpLoggingLevel(loggingLevel: HttpLoggingInterceptor.Level)

    }


}
