package com.vascome.fogtail.di.appmodules;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.vascome.fogtail.utils.AnalyticsModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */
@Module
public class ModelsModule {

    @Provides
    @NonNull
    @Singleton
    public AnalyticsModel provideAnalyticsModel(Application app) {
        return new GoogleFirebaseAppAnalytics(app);
    }

    static class GoogleFirebaseAppAnalytics implements AnalyticsModel {

        @NonNull
        private final Application app;
        private FirebaseAnalytics mFirebaseAnalytics;

        @Inject
        GoogleFirebaseAppAnalytics(@NonNull Application app) {
            this.app = app;
        }

        @Override
        public void init() {
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.app);
        }

        @Override
        public void sendEvent(@NonNull String eventName) {
            Bundle bundle = new Bundle();
            bundle.putString("name", eventName);
            mFirebaseAnalytics.logEvent("event", bundle);
        }

        @Override
        public void sendError(@NonNull String message, @NonNull Throwable error) {
            Bundle bundle = new Bundle();
            bundle.putString("name", message);
            bundle.putSerializable("error", error);
            mFirebaseAnalytics.logEvent("error_event", bundle);
        }

    }
/*
    static class FlurryAppAnalytics implements AnalyticsModel {

        @NonNull
        private final Application app;

        FlurryAppAnalytics(@NonNull Application app) {
            this.app = app;
        }

        @Override
        public void init() {
            new FlurryAgent.Builder()
                    .withLogEnabled(true)
                    .withCaptureUncaughtExceptions(true)
                    .withContinueSessionMillis(10)
                    .build(this.app, "RGGP7ZQ2QYTTNDQWPBPP");
        }

        @Override
        public void sendEvent(@NonNull String eventName) {

            FlurryAgent.logEvent(eventName);
        }

        @Override
        public void sendError(@NonNull String message, @NonNull Throwable error) {
            FlurryAgent.onError("error", message, error);
        }
    }
    */

}
