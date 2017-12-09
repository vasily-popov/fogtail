package com.vascome.fogtail;

import android.app.Activity;
import android.app.Application;

import com.vascome.fogtail.developer_settings.DeveloperSettingsModel;
import com.vascome.fogtail.di.DaggerAppComponent;
import com.vascome.fogtail.models.AnalyticsModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

public class FogtailApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject
    AnalyticsModel analyticsModel;

    @Inject
    DeveloperSettingsModel developerSettingModel;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent
                .builder()
                .application(this)
                .context(this)
                .build()
                .inject(this);

        analyticsModel.init();

        if (BuildConfig.DEBUG) {

            Timber.plant(new Timber.DebugTree());
            developerSettingModel.apply();
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}