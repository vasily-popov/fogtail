package com.vascome.fogtail;

public class FogtailIntegrationTestApp extends FogtailApplication {

    /*
    @NonNull
    @Override
    protected DaggerAppComponent.Builder prepareApplicationComponent() {
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
    */
}
