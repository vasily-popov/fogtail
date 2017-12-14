package com.vascome.fogtail;

import android.app.Application;
import android.support.annotation.NonNull;

import com.vascome.fogtail.data.api.ApiConfiguration;
import com.vascome.fogtail.di.DaggerAppComponent;
import com.vascome.fogtail.di.appmodules.AnalyticsModule;
import com.vascome.fogtail.di.appmodules.ApiModule;
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule;
import com.vascome.fogtail.utils.AnalyticsModel;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

import static org.mockito.Mockito.mock;

public class FogtailIntegrationTestApp extends FogtailApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder()
                .application(this)
                .apiModule(new ApiModule() {
                    @NonNull
                    @Override
                    public ApiConfiguration provideConfiguration() {
                        return () -> "https://test/";
                    }
                }).analyticsModule(new AnalyticsModule() {
                    @NonNull
                    @Override
                    public AnalyticsModel provideAnalyticsModel(Application application) {
                        return mock(AnalyticsModel.class);
                    }
                })
                .devSettingsModule(new DeveloperSettingsModule())
                .build();
        return appComponent;
    }
}
