package com.vascome.fogtail;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.vascome.fogtail.api.ApiModule;
import com.vascome.fogtail.api.FogtailRestApi;
import com.vascome.fogtail.appmodules.ApplicationModule;
import com.vascome.fogtail.developer_settings.DeveloperSettingsComponent;
import com.vascome.fogtail.developer_settings.DeveloperSettingsModel;
import com.vascome.fogtail.developer_settings.DeveloperSettingsModule;
import com.vascome.fogtail.developer_settings.LeakCanaryProxy;
import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.models.ModelsModule;
import com.vascome.fogtail.network.NetworkModule;
import com.vascome.fogtail.network.OkHttpInterceptorsModule;
import com.vascome.fogtail.ui.carousel.CarouselAppFragment;
import com.vascome.fogtail.ui.gallery.GalleryAppFragment;
import com.vascome.fogtail.ui.list.ListAppFragment;
import com.vascome.fogtail.ui.main.MainActivity;
import com.vascome.fogtail.ui.stack.StackAppFragment;
import com.vascome.fogtail.ui.table.GridAppFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        OkHttpInterceptorsModule.class,
        ApiModule.class,
        //AsyncJobsModule.class,
        ModelsModule.class,
        DeveloperSettingsModule.class,
})
public interface ApplicationComponent {


    // Provide Gson from the real app to the tests without need in injection to the test.
    @NonNull
    Gson gson();

    // Provide FogtailRestApi from the real app to the tests without need in injection to the test.
    @NonNull
    FogtailRestApi provideRestApi();
/*
    // Provide AsyncJobObserver from the real app to the tests without need in injection to the test.
    @NonNull
    AsyncJobsObserver asyncJobsObserver();
*/
    // Provide LeakCanary without injection to leave.
    @NonNull
    LeakCanaryProxy leakCanaryProxy();

    @NonNull
    ListAppFragment.ListFragmentComponent plus(@NonNull ListAppFragment.ListFragmentModule listFragmentModule);

    @NonNull
    DeveloperSettingsComponent plusDeveloperSettingsComponent();

    @NonNull
    AnalyticsModel analyticsModel();

    DeveloperSettingsModel developerSettingModel();

    void inject(@NonNull MainActivity mainActivity);

    @NonNull
    GridAppFragment.GridFragmentComponent plus(@NonNull GridAppFragment.GridFragmentModule gridFragmentModule);
    @NonNull
    GalleryAppFragment.GalleryFragmentComponent plus(@NonNull GalleryAppFragment.GalleryFragmentModule galleryFragmentModule);
    @NonNull
    CarouselAppFragment.CarouselFragmentComponent plus(@NonNull CarouselAppFragment.CarouselFragmentModule carouselFragmentModule);
    @NonNull
    StackAppFragment.StackFragmentComponent plus(@NonNull StackAppFragment.StackFragmentModule stackFragmentModule);
}