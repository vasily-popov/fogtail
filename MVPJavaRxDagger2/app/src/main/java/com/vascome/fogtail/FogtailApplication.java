package com.vascome.fogtail;

import android.app.Activity;
import android.app.Application;

import com.vascome.fogtail.developer_settings.DeveloperSettingsModel;
import com.vascome.fogtail.di.DaggerAppComponent;
import com.vascome.fogtail.models.AnalyticsModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

public class FogtailApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().context(this).application(this).build();
    }

    @Inject
    AnalyticsModel analyticsModel;

    @Inject
    DeveloperSettingsModel developerSettingModel;

    @Override
    public void onCreate() {
        super.onCreate();

        analyticsModel.init();

        if (BuildConfig.DEBUG) {

            Timber.plant(new Timber.DebugTree());
            developerSettingModel.apply();
        }
    }
}