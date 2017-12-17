package com.vascome.fogtail.functional_tests;

import android.app.Application;

import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.di.AppComponent;
import com.vascome.fogtail.di.FunctionalAnalyticsModule;
import com.vascome.fogtail.di.FunctionalTestModule;
import com.vascome.fogtail.di.appmodules.ApplicationModule;
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule;
import com.vascome.fogtail.di.appmodules.OkHttpInterceptorsModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

public class FogtailFunctionalTestApp extends FogtailApplication {

    @Singleton
    @Component( modules = {
            ApplicationModule.class,
            FunctionalTestModule.class,
            FunctionalAnalyticsModule.class,
            OkHttpInterceptorsModule.class,
            DeveloperSettingsModule.class,
            AndroidSupportInjectionModule.class
    })
    public interface MockAppComponent extends AppComponent {
        //void inject(FogtailRestApiIntegrationTest test);

        @Component.Builder
        interface Builder {

            @BindsInstance
            Builder application(Application app);

            MockAppComponent build();
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerFogtailFunctionalTestApp_MockAppComponent.builder()
                .application(this)
                .build();
        return appComponent;
    }
}
