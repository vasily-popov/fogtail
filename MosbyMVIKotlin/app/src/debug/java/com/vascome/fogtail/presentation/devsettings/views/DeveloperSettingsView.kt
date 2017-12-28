package com.vascome.fogtail.presentation.devsettings.views

import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.Observable
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

interface DeveloperSettingsView:MvpView {


    /**
     * The intent to load stuff on start
     *
     * @return The emitted item boolean
     */
    fun loadOnStartIntent(): Observable<Boolean>

    /**
     * The intent to react on stetho toggle
     *
     * @return The emitted item boolean
     */
    fun stethoSwitchChecked(): Observable<Boolean>

    /**
     * The intent to react on leak canary toggle
     *
     * @return The emitted item boolean
     */
    fun leakCanarySwitchChecked(): Observable<Boolean>

    /**
     * The intent to react on tiny dancer toggle
     *
     * @return The emitted item boolean
     */
    fun tinyDancerSwitchChecked(): Observable<Boolean>

    /**
     * The intent to react on level change
     *
     * @return The emitted item integer
     */
    fun levelChanged(): Observable<HttpLoggingInterceptor.Level>


    /**
     * Renders the viewState
     */
    fun render(viewState: DevViewState)

    interface DeveloperSettingsFeedback {
        val httpLoggingLevel: HttpLoggingInterceptor.Level

        val tinyDancerObservable: Observable<Boolean>
        val stethoObservable: Observable<Boolean>
        val leakCanaryObservable: Observable<Boolean>
        val httpLevelObservable: Observable<HttpLoggingInterceptor.Level>
    }
}
