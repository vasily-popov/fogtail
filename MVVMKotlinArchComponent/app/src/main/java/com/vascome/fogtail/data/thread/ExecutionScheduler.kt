package com.vascome.fogtail.data.thread

import io.reactivex.Scheduler

@Suppress("FunctionName")
/**
 * Created by vasilypopov on 12/16/17
 * Copyright (c) 2017 MVVMJavaRx. All rights reserved.
 */

interface ExecutionScheduler {

    fun UI(): Scheduler
    fun IO(): Scheduler
    fun COMPUTATION(): Scheduler
}
