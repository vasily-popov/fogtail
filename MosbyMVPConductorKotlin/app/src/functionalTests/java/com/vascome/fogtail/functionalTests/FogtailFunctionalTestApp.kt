package com.vascome.fogtail.functionalTests

import android.app.Application

import com.vascome.fogtail.FogtailApplication
import com.vascome.fogtail.di.AppComponent
import com.vascome.fogtail.di.FunctionalAnalyticsModule
import com.vascome.fogtail.di.FunctionalTestModule
import com.vascome.fogtail.di.appmodules.ApplicationModule
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule
import com.vascome.fogtail.di.appmodules.OkHttpInterceptorsModule

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

class FogtailFunctionalTestApp : FogtailApplication() {

    @Singleton
    @Component(modules = [(ApplicationModule::class), (FunctionalTestModule::class), (FunctionalAnalyticsModule::class), (OkHttpInterceptorsModule::class), (DeveloperSettingsModule::class), (AndroidSupportInjectionModule::class)])
    interface MockAppComponent : AppComponent {
        //void inject(FogtailRestApiIntegrationTest test);

        @Component.Builder
        interface Builder {

            @BindsInstance
            fun application(app: Application): Builder

            fun build(): MockAppComponent
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerFogtailFunctionalTestApp_MockAppComponent.builder()
                .application(this)
                .build()
        return appComponent
    }
}
