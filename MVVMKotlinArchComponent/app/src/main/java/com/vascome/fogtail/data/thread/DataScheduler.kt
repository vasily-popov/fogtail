package com.vascome.fogtail.data.thread

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * MainThread (UI Thread) implementation based on a [Scheduler]
 * which will execute actions on the Android UI thread
 */
@Suppress("FunctionName")
@Singleton
class DataScheduler
@Inject internal constructor() : ExecutionScheduler {

    override fun UI(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    override fun IO(): Scheduler {
        return Schedulers.io()
    }

    override fun COMPUTATION(): Scheduler {
        return Schedulers.computation()
    }
}
