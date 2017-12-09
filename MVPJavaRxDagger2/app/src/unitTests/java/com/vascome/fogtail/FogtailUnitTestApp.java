package com.vascome.fogtail;


public class FogtailUnitTestApp extends FogtailApplication {
    /*
    @NonNull
    @Override
    protected DaggerAppComponent.Builder prepareApplicationComponent() {
        return super.prepareApplicationComponent()
                .modelsModule(new ModelsModule() {
                    @NonNull
                    @Override
                    public AnalyticsModel provideAnalyticsModel(@NonNull Application app) {
                        return mock(AnalyticsModel.class); // We don't need real analytics in Unit tests.
                    }
                })
                .developerSettingsModule(new DeveloperSettingsModule());
    }
    */
}
