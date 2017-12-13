package com.vascome.fogtail.di.appmodules;

import android.support.annotation.NonNull;

import com.vascome.fogtail.data.thread.DataThread;
import com.vascome.fogtail.data.thread.JobExecutor;
import com.vascome.fogtail.presentation.base.domain.executor.ExecutionThread;
import com.vascome.fogtail.presentation.base.domain.executor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

@Module
public class ThreadModule {

    @Provides
    @NonNull
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @NonNull
    @Singleton
    ExecutionThread providePostExecutionThread(DataThread dataThread) {
        return dataThread;
    }

}
