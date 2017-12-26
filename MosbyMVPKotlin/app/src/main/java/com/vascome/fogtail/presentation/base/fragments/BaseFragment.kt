package com.vascome.fogtail.presentation.base.fragments
import android.support.v4.app.Fragment
import com.vascome.fogtail.di.Injectable
import com.vascome.fogtail.utils.LeakCanaryProxy

import javax.inject.Inject


/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

abstract class BaseFragment : Fragment(), Injectable {

    @Inject
    lateinit var leakCanaryProxy: LeakCanaryProxy

    private val isFragmentAlive: Boolean
        get() = activity != null && isAdded && !isDetached && view != null && !isRemoving

    protected fun runIfFragmentAlive(runnable: Runnable) {
        if (isFragmentAlive) {
            runnable.run()
        }
    }

    override fun onDestroy() {
        leakCanaryProxy.watch(this)
        super.onDestroy()
    }
}
