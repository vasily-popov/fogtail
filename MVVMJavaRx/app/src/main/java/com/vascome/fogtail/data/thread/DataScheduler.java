package com.vascome.fogtail.data.thread;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * MainThread (UI Thread) implementation based on a {@link Scheduler}
 * which will execute actions on the Android UI thread
 */
@Singleton
public class DataScheduler implements ExecutionScheduler {

    @Inject
    DataScheduler() {}

    public Scheduler UI() {
        return AndroidSchedulers.mainThread();
    }

    public Scheduler IO() {
        return Schedulers.io();
    }

    public Scheduler COMPUTATION() {
        return Schedulers.computation();
    }
}
