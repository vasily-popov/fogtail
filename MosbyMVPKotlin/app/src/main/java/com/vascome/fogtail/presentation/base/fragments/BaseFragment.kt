package com.vascome.fogtail.presentation.base.fragments

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.viewstate.MvpViewStateFragment
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState
import com.vascome.fogtail.di.Injectable
import com.vascome.fogtail.utils.LeakCanaryProxy

import javax.inject.Inject


/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

abstract class BaseFragment<V : MvpView, P : MvpPresenter<V>, VS: ViewState<V>> : MvpViewStateFragment<V, P, VS>(), Injectable {

    @Inject
    lateinit var leakCanaryProxy: LeakCanaryProxy

    protected fun runOnUI(runnable: Runnable) {
        runnable.run()
    }
    override fun onDestroy() {
        leakCanaryProxy.watch(this)
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}
