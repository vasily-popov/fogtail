package com.vascome.fogtail.di.ui.main.basemodule;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.models.RecAreaItemsModel;
import com.vascome.fogtail.ui.main.collectionbase.CollectionPresenter;

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
                                                     @NonNull AnalyticsModel analyticsModel) {
        return new CollectionPresenter(itemsModel, analyticsModel);
    }
}