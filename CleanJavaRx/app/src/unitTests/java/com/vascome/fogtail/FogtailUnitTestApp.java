package com.vascome.fogtail;

import android.app.Application;

import com.vascome.fogtail.di.AppComponent;
import com.vascome.fogtail.di.IntegrationTestModule;
import com.vascome.fogtail.di.MockAnalyticsModule;
import com.vascome.fogtail.di.appmodules.ApplicationModule;
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule;
import com.vascome.fogtail.di.appmodules.OkHttpInterceptorsModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

public class FogtailUnitTestApp extends FogtailApplication {

    @Singleton
    @Component( modules = {
            ApplicationModule.class,
            IntegrationTestModule.class,
            MockAnalyticsModule.class,
            OkHttpInterceptorsModule.class,
            DeveloperSettingsModule.class,
            AndroidSupportInjectionModule.class
    })
    public interface MockAppComponent extends AppComponent {
        //void inject(FogtailRestApiIntegrationTest test);

        @Component.Builder
        interface Builder {

            @BindsInstance
            MockAppComponent.Builder application(Application app);
            MockAppComponent.Builder devSettingsModule(DeveloperSettingsModule module);

            MockAppComponent build();
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerFogtailUnitTestApp_MockAppComponent.builder()
                .application(this)
                .devSettingsModule(new DeveloperSettingsModule())
                .build();
        return appComponent;
    }

}
