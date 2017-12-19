package com.vascome.fogtail.di.appmodules

import com.vascome.fogtail.data.thread.DataThread
import com.vascome.fogtail.data.thread.JobExecutor
import com.vascome.fogtail.presentation.base.domain.executor.ExecutionThread
import com.vascome.fogtail.presentation.base.domain.executor.ThreadExecutor

import javax.inject.Singleton

import dagger.Module
import dagger.Provides


/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

@Module
class ThreadModule {

    @Provides
    @Singleton
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @Singleton
    internal fun providePostExecutionThread(dataThread: DataThread): ExecutionThread {
        return dataThread
    }

}
