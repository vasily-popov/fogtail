package com.vascome.fogtail.di.ui.main;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.di.AppComponent;
import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.models.AppImageLoader;
import com.vascome.fogtail.models.RecAreaItemsModel;
import com.vascome.fogtail.ui.main.MainActivity;

import javax.inject.Named;

import dagger.Component;

import static com.vascome.fogtail.di.appmodules.ApplicationModule.MAIN_THREAD_HANDLER;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Component(dependencies = AppComponent.class,
        modules = {
                CollectionModule.class,
})
@ActivityScope
public interface CollectionComponent {

    @NonNull
    Context context();
    @NonNull
    AnalyticsModel analyticsModel();

    @Named(MAIN_THREAD_HANDLER)
    Handler mainThreadHandler();

    @NonNull
    AppImageLoader imageLoader();

    @NonNull
    RecAreaItemsModel itemModel();


    void inject(@NonNull MainActivity mainActivity);
}

