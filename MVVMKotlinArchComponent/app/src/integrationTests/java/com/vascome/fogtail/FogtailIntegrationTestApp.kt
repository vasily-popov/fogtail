package com.vascome.fogtail

import android.app.Application

import com.vascome.fogtail.di.AppComponent
import com.vascome.fogtail.di.IntegrationTestModule
import com.vascome.fogtail.di.MockAnalyticsModule
import com.vascome.fogtail.di.appmodules.ApplicationModule
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule
import com.vascome.fogtail.di.appmodules.OkHttpInterceptorsModule
import com.vascome.fogtail.integration_tests.api.FogtailRestApiIntegrationTest
import com.vascome.fogtail.integration_tests.api.entities.RecAreaItemTest

import javax.inject.Singleton

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule

class FogtailIntegrationTestApp : FogtailApplication() {

    @Singleton
    @Component(modules =
    [(ApplicationModule::class),
        (IntegrationTestModule::class),
        (MockAnalyticsModule::class),
        (OkHttpInterceptorsModule::class),
        (DeveloperSettingsModule::class),
        (AndroidSupportInjectionModule::class)])
    interface MockAppComponent : AppComponent {
        fun inject(test: FogtailRestApiIntegrationTest)
        fun inject(test: RecAreaItemTest)

        @Component.Builder
        interface Builder {

            @BindsInstance
            fun application(app: Application): Builder

            fun devSettingsModule(module: DeveloperSettingsModule): Builder

            fun build(): MockAppComponent
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerFogtailIntegrationTestApp_MockAppComponent.builder()
                .application(this)
                .devSettingsModule(DeveloperSettingsModule())
                .build()
        return appComponent
    }
}
