package com.vascome.fogtail.ui.detail.di;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.ui.detail.RecAreaDetailFragment;
import com.vascome.fogtail.ui.di.CollectionComponent;

import dagger.Component;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Component(dependencies = CollectionComponent.class)
@FragmentScope
public interface CollectionDetailComponent {

    void inject(@NonNull RecAreaDetailFragment fragment);
}
