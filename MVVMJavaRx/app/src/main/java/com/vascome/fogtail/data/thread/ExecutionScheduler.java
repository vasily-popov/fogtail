package com.vascome.fogtail.data.thread;

import io.reactivex.Scheduler;

/**
 * Created by vasilypopov on 12/16/17
 * Copyright (c) 2017 MVVMJavaRx. All rights reserved.
 */

public interface ExecutionScheduler {

    Scheduler UI();
    Scheduler IO();
    Scheduler COMPUTATION();
}
