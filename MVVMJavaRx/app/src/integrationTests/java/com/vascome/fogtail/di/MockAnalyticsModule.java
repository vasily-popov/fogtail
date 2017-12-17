package com.vascome.fogtail.di;

import android.app.Application;
import android.support.annotation.NonNull;

import com.vascome.fogtail.utils.AnalyticsModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

/**
 * Created by vasilypopov on 12/14/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */
@Module
public class MockAnalyticsModule {
    @Provides
    @NonNull
    @Singleton
    public AnalyticsModel provideAnalyticsModel(Application app) {
        return mock(AnalyticsModel.class);
    }

}

