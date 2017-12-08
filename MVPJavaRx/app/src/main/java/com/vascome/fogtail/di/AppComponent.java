package com.vascome.fogtail.di;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.vascome.fogtail.api.FogtailRestApi;
import com.vascome.fogtail.developer_settings.DeveloperSettingsComponent;
import com.vascome.fogtail.developer_settings.DeveloperSettingsModel;
import com.vascome.fogtail.developer_settings.LeakCanaryProxy;
import com.vascome.fogtail.di.appmodules.ApiModule;
import com.vascome.fogtail.di.appmodules.ApplicationModule;
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule;
import com.vascome.fogtail.di.appmodules.ModelsModule;
import com.vascome.fogtail.di.appmodules.NetworkModule;
import com.vascome.fogtail.di.appmodules.OkHttpInterceptorsModule;
import com.vascome.fogtail.di.ui.detail.CollectionDetailComponent;
import com.vascome.fogtail.di.ui.main.CollectionComponent;
import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.models.AppImageLoader;
import com.vascome.fogtail.ui.base.other.ViewModifier;
import com.vascome.fogtail.utils.schedulers.SchedulerProvider;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

import static com.vascome.fogtail.di.appmodules.ApplicationModule.MAIN_THREAD_HANDLER;
import static com.vascome.fogtail.di.appmodules.DeveloperSettingsModule.MAIN_ACTIVITY_VIEW_MODIFIER;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */


@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        OkHttpInterceptorsModule.class,
        ApiModule.class,
        ModelsModule.class,
        DeveloperSettingsModule.class,
})
public interface AppComponent {


    // Provide Gson from the real app to the tests without need in injection to the test.
    @NonNull
    Gson gson();

    // Provide FogtailRestApi from the real app to the tests without need in injection to the test.
    @NonNull
    FogtailRestApi provideRestApi();

    // Provide LeakCanary without injection to leave.
    @NonNull
    LeakCanaryProxy leakCanaryProxy();

    @NonNull
    AnalyticsModel analyticsModel();

    DeveloperSettingsModel developerSettingModel();

    //submodules

    @NonNull
    DeveloperSettingsComponent.Builder developerSettingsComponent();

    @NonNull
    CollectionComponent.Builder collectionComponent();

    @NonNull
    CollectionDetailComponent.Builder collectionDetailComponent();

}