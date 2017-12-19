package com.vascome.fogtail

import android.app.Application

import com.vascome.fogtail.di.AppComponent
import com.vascome.fogtail.di.IntegrationTestModule
import com.vascome.fogtail.di.MockAnalyticsModule
import com.vascome.fogtail.di.appmodules.ApplicationModule
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule
import com.vascome.fogtail.di.appmodules.OkHttpInterceptorsModule

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

class FogtailUnitTestApp : FogtailApplication() {

    @Singleton
    @Component(modules =
        [(ApplicationModule::class),
            (IntegrationTestModule::class),
            (MockAnalyticsModule::class),
            (OkHttpInterceptorsModule::class),
            (DeveloperSettingsModule::class),
            (AndroidSupportInjectionModule::class)])
    interface MockAppComponent : AppComponent {
        //void inject(FogtailRestApiIntegrationTest test);

        @Component.Builder
        interface Builder {

            @BindsInstance
            fun application(app: Application): MockAppComponent.Builder

            fun devSettingsModule(module: DeveloperSettingsModule): MockAppComponent.Builder

            fun build(): MockAppComponent
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerFogtailUnitTestApp_MockAppComponent.builder()
                .application(this)
                .devSettingsModule(DeveloperSettingsModule())
                .build()
        return appComponent
    }

}
