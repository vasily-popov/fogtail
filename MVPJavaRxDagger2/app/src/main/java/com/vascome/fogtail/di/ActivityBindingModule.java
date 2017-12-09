package com.vascome.fogtail.di;

import com.vascome.fogtail.di.ui.detail.DetailActivityModule;
import com.vascome.fogtail.di.ui.main.MainActivityModule;
import com.vascome.fogtail.ui.detail.RecAreaItemDetailActivity;
import com.vascome.fogtail.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vasilypopov on 12/9/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Module
public abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = DetailActivityModule.class)
    abstract RecAreaItemDetailActivity recAreaItemDetailActivity();
}
