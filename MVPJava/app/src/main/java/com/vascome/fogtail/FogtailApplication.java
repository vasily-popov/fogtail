package com.vascome.fogtail;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.vascome.fogtail.api.ApiModule;
import com.vascome.fogtail.appmodule.ApplicationModule;
import com.vascome.fogtail.models.AnalyticsModel;

import timber.log.Timber;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

public class FogtailApplication extends Application {
    private ApplicationComponent applicationComponent;

    // Prevent need in a singleton (global) reference to the application object.
    @NonNull
    public static FogtailApplication get(@NonNull Context context) {
        return (FogtailApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();

        AnalyticsModel analyticsModel = applicationComponent.analyticsModel();

        analyticsModel.init();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());

            //DeveloperSettingsModel developerSettingModel = applicationComponent.developerSettingModel();
            //developerSettingModel.apply();
        }
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                // This url may be changed dynamically for tests! See ChangeableBaseUrl.
                .apiModule(new ApiModule("https://raw.githubusercontent.com/vascome/fogtail"));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}