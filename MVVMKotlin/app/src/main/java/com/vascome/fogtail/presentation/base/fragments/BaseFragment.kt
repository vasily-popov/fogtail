package com.vascome.fogtail.presentation.base.fragments

import com.vascome.fogtail.utils.LeakCanaryProxy
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

abstract class BaseFragment : DaggerFragment() {

    @Inject
    lateinit var leakCanaryProxy: LeakCanaryProxy

    protected var disposables = CompositeDisposable()

    private val isFragmentAlive: Boolean
        get() = activity != null && isAdded && !isDetached && view != null && !isRemoving

    protected fun runIfFragmentAlive(runnable: Runnable) {
        if (isFragmentAlive) {
            runnable.run()
        }
    }

    override fun onPause() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
        super.onPause()
    }

    override fun onResume() {
        disposables = CompositeDisposable()
        super.onResume()
    }

    override fun onDestroy() {
        leakCanaryProxy.watch(this)
        super.onDestroy()
    }
}
