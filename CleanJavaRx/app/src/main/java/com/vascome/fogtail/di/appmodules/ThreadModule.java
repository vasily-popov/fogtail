package com.vascome.fogtail.di.appmodules;

import android.support.annotation.NonNull;

import com.vascome.fogtail.data.thread.ThreadPoolScheduler;
import com.vascome.fogtail.data.thread.ThreadScheduler;

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
    public ThreadScheduler threadPoolScheduler() {
        return new ThreadPoolScheduler();
    }
}
