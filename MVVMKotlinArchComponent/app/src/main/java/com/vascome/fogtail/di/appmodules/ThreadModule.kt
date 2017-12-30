package com.vascome.fogtail.di.appmodules

import com.vascome.fogtail.data.thread.DataScheduler
import com.vascome.fogtail.data.thread.ExecutionScheduler
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

@Module
abstract class ThreadModule {

    @Binds
    @Singleton
    internal abstract fun dataScheduler(scheduler:DataScheduler):ExecutionScheduler

}
