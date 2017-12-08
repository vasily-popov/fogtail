package com.vascome.fogtail.ui.stack;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.ui.collectionbase.CollectionFragmentModule;
import com.vascome.fogtail.ui.di.CollectionComponent;

import dagger.Component;

/**
 * Created by vasilypopov on 12/8/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Component(dependencies = CollectionComponent.class,
        modules = {
                CollectionFragmentModule.class,
        })
@FragmentScope
public interface StackFragmentComponent {
    void inject(@NonNull StackAppFragment itemsFragment);
}
