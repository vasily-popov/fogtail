package com.vascome.fogtail.data.thread

import com.vascome.fogtail.presentation.base.domain.executor.ExecutionThread

import javax.inject.Inject
import javax.inject.Singleton

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * MainThread (UI Thread) implementation based on a [Scheduler]
 * which will execute actions on the Android UI thread
 */
@Singleton
class DataThread
@Inject internal constructor() : ExecutionThread {

    override val uiScheduler: Scheduler
        get() = AndroidSchedulers.mainThread()

    override val ioScheduler: Scheduler
        get() = Schedulers.io()
}
