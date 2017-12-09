package com.vascome.fogtail.di.appmodules;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.vascome.fogtail.utils.schedulers.SchedulerProvider;
import com.vascome.fogtail.utils.schedulers.SchedulerProviderImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by vasilypopov on 12/9/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */
@Module
public class SchedulerModule {

    public static final String MAIN_THREAD_HANDLER = "main_thread_handler";

    @Provides
    @NonNull
    @Named(MAIN_THREAD_HANDLER)
    @Singleton
    public Handler provideMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    @NonNull
    @Singleton
    public SchedulerProvider provideScheduler() {
        return new SchedulerProviderImpl();
    }


}
