package com.vascome.fogtail.di;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.di.appmodules.ApiModule;
import com.vascome.fogtail.di.appmodules.ApplicationModule;
import com.vascome.fogtail.di.appmodules.DeveloperSettingsModule;
import com.vascome.fogtail.di.appmodules.ModelsModule;
import com.vascome.fogtail.di.appmodules.NetworkModule;
import com.vascome.fogtail.di.appmodules.OkHttpInterceptorsModule;
import com.vascome.fogtail.di.appmodules.SchedulerModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */



@Singleton
@Component(modules = {
        ApplicationModule.class,
        ActivityBindingModule.class,
        NetworkModule.class,
        OkHttpInterceptorsModule.class,
        ApiModule.class,
        SchedulerModule.class,
        ModelsModule.class,
        DeveloperSettingsModule.class,
        AndroidSupportInjectionModule.class
})
public interface AppComponent extends AndroidInjector<FogtailApplication> {

    Gson gson();

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application app);

        Builder apiModule(ApiModule module); //this allow to specify module in test
        Builder modelsModule(ModelsModule module); //this allow to specify module in test
        Builder devSettingsModule(DeveloperSettingsModule module); //this allow to specify module in test
        AppComponent build();
    }
}