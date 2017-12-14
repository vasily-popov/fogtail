package com.vascome.fogtail.di;

import android.app.Application;
import android.support.annotation.NonNull;

import com.vascome.fogtail.utils.AnalyticsModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by vasilypopov on 12/14/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */
@Module
public class FunctionalAnalyticsModule {

    @Provides
    @NonNull
    @Singleton
    public AnalyticsModel provideAnalyticsModel(@NonNull Application app) {
        // We don't need real analytics in Functional tests, but let's just log it instead!
        return new AnalyticsModel() {

            // We'll check that application initializes Analytics before working with it!
            private volatile boolean isInitialized;

            @Override
            public void init() {
                isInitialized = true;
                Timber.d("Analytics: initialized.");
            }

            @Override
            public void sendEvent(@NonNull String eventName) {
                throwIfNotInitialized();
                Timber.d("Analytics: send event %s", eventName);
            }

            @Override
            public void sendError(@NonNull String message, @NonNull Throwable error) {
                throwIfNotInitialized();
                Timber.e(error, message);
            }

            private void throwIfNotInitialized() {
                if (!isInitialized) {
                    throw new AssertionError("Analytics was not initialized!");
                }
            }
        };
    }

}
