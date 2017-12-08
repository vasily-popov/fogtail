package com.vascome.fogtail.di.ui.detail;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.di.AppComponent;
import com.vascome.fogtail.models.AppImageLoader;
import com.vascome.fogtail.ui.detail.RecAreaItemDetailActivity;

import javax.inject.Named;

import dagger.Component;

import static com.vascome.fogtail.di.appmodules.ApplicationModule.MAIN_THREAD_HANDLER;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */


@Component(dependencies = AppComponent.class)
@ActivityScope
public interface CollectionDetailComponent {

    @NonNull
    Context context();

    @Named(MAIN_THREAD_HANDLER)
    Handler mainThreadHandler();

    @NonNull
    AppImageLoader imageLoader();


    void inject(@NonNull RecAreaItemDetailActivity activity);
}
