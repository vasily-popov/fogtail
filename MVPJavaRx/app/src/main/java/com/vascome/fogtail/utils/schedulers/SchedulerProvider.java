package com.vascome.fogtail.utils.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

public interface SchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}

