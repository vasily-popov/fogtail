package com.vascome.fogtail.di.screens.main;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.vascome.fogtail.data.api.FogtailRestApi;
import com.vascome.fogtail.data.gateway.RecAreaGateway;
import com.vascome.fogtail.data.gateway.RecItemsDataSource;
import com.vascome.fogtail.data.thread.ThreadScheduler;
import com.vascome.fogtail.di.ActivityScope;
import com.vascome.fogtail.screens.base.domain.UseCaseHandler;
import com.vascome.fogtail.screens.main.CollectionContract;
import com.vascome.fogtail.screens.main.CollectionRouter;
import com.vascome.fogtail.screens.main.MainActivity;
import com.vascome.fogtail.screens.main.domain.usecase.GetItemsTask;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.vascome.fogtail.di.appmodules.ApplicationModule.MAIN_THREAD_HANDLER;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */
@Module
public class CollectionModule {

    private MainActivity activity;

    public CollectionModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    @NonNull
    @ActivityScope
    public RecItemsDataSource provideItemsDataSource(@NonNull FogtailRestApi fogtailRestApi) {
        return new RecAreaGateway(fogtailRestApi);
    }

    @Provides
    @NonNull
    @ActivityScope
    public CollectionContract.Router provideRouter() {
        return new CollectionRouter(activity);
    }


    @Provides
    @NonNull
    @ActivityScope
    public UseCaseHandler provideHandler(ThreadScheduler scheduler, @Named(MAIN_THREAD_HANDLER) Handler handler) {
        return new UseCaseHandler(scheduler, handler);
    }

    @Provides
    @NonNull
    @ActivityScope
    public GetItemsTask provideTask(RecItemsDataSource source) {
        return new GetItemsTask(source);
    }
}