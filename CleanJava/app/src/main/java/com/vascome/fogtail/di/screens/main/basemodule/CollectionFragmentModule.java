package com.vascome.fogtail.di.screens.main.basemodule;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.screens.main.CollectionContract;
import com.vascome.fogtail.screens.main.CollectionPresenter;
import com.vascome.fogtail.screens.main.RecAreaItemsModel;
import com.vascome.fogtail.utils.AnalyticsModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Module
public class CollectionFragmentModule {

    @Provides
    @NonNull
    @FragmentScope
    public CollectionPresenter provideItemsPresenter(@NonNull RecAreaItemsModel itemsModel,
                                                     @NonNull AnalyticsModel analyticsModel,
                                                     @NonNull CollectionContract.Router router) {
        return new CollectionPresenter(itemsModel, analyticsModel, router);
    }
}