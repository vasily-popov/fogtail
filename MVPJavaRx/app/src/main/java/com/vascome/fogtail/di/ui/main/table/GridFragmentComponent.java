package com.vascome.fogtail.di.ui.main.table;

import android.support.annotation.NonNull;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.di.ui.main.basemodule.CollectionFragmentModule;
import com.vascome.fogtail.di.ui.main.CollectionComponent;
import com.vascome.fogtail.ui.main.table.GridAppFragment;

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
public interface GridFragmentComponent {

    void inject(@NonNull GridAppFragment itemsFragment);
}