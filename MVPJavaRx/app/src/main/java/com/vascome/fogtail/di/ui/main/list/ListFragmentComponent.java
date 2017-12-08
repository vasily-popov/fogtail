package com.vascome.fogtail.di.ui.main.list;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.di.ui.main.basemodule.CollectionFragmentModule;
import com.vascome.fogtail.di.ui.main.CollectionComponent;
import com.vascome.fogtail.ui.main.list.ListAppFragment;

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
