package com.vascome.fogtail;

import android.app.Application;
import android.support.annotation.NonNull;

import com.vascome.fogtail.api.ApiConfiguration;
import com.vascome.fogtail.api.ApiModule;
import com.vascome.fogtail.developer_settings.DeveloperSettingsModule;
import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.models.ModelsModule;

import static org.mockito.Mockito.mock;

public class FogtailIntegrationTestApp extends FogtailApplication {

    @NonNull
    @Override
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return super.prepareApplicationComponent()
                .apiModule(new ApiModule() {
                    @NonNull
                    @Override
                    public ApiConfiguration provideConfiguration() {
                        return () -> "/";
                    }
                })
                .modelsModule(new ModelsModule() {
                    @NonNull
                    @Override
                    public AnalyticsModel provideAnalyticsModel(@NonNull Application app) {
                        return mock(AnalyticsModel.class); // We don't need real analytics in integration tests.
                    }
                })
                .developerSettingsModule(new DeveloperSettingsModule());
    }
}
