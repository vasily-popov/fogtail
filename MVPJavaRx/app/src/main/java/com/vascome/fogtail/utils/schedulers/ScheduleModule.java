package com.vascome.fogtail.utils.schedulers;

import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Module
public class ScheduleModule {

    @Provides
    @Singleton
    @NonNull
    public SchedulerProviderImpl provideScheduler() {
        return new SchedulerProviderImpl();
    }
}


