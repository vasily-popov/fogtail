package com.vascome.fogtail.presentation.base.controllers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.conductor.viewstate.MvpViewStateController
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState
import com.vascome.fogtail.utils.LeakCanaryProxy

import javax.inject.Inject


/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

abstract class BaseViewController<V : MvpView, P : MvpPresenter<V>, VS: ViewState<V>>
    : MvpViewStateController<V, P, VS>() {

    @Inject
    lateinit var leakCanaryProxy: LeakCanaryProxy

    protected var isInjected = false

    protected fun runOnUI(runnable: Runnable) {
        runnable.run()
    }

    override fun onDestroy() {
        leakCanaryProxy.watch(this)
        super.onDestroy()
    }

    override fun onViewStateInstanceRestored(instanceStateRetained: Boolean) {
        //no-op
    }

    override fun onNewViewStateInstance() {
        //no-op
    }
}
