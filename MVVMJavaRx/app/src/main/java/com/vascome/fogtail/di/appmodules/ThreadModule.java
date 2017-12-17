package com.vascome.fogtail.di.appmodules;

import android.support.annotation.NonNull;

import com.vascome.fogtail.data.thread.DataScheduler;
import com.vascome.fogtail.data.thread.ExecutionScheduler;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;


/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

@Module
public abstract class ThreadModule {

    @Binds
    @NonNull
    @Singleton
    abstract ExecutionScheduler dataScheduler(DataScheduler scheduler);

}
