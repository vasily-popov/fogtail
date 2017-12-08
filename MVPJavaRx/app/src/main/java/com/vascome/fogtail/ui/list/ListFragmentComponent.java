package com.vascome.fogtail.ui.list;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.ui.collectionbase.CollectionFragmentModule;
import com.vascome.fogtail.ui.di.CollectionComponent;

import dagger.Component;
import android.support.annotation.NonNull;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Component(dependencies = CollectionComponent.class,
            modules = {
                CollectionFragmentModule.class,
            })
@FragmentScope
public interface ListFragmentComponent {

    void inject(@NonNull ListAppFragment itemsFragment);
}
