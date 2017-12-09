package com.vascome.fogtail.di.ui.detail;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.ui.detail.RecAreaDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vasilypopov on 12/9/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */
@Module
public abstract class DetailActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract RecAreaDetailFragment recAreaDetailFragment();

    /*
    @Provides
    @Named("someId")
    public static int provideSomeId(MyFragment myFragment) {
        return myFragment.getSomeId();
    }
    */

}
