package com.vascome.fogtail.di.ui.dev_settings;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.ui.dev_settings.fragments.DeveloperSettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vasilypopov on 12/9/17
 * Copyright (c) 2017 MVPJavaRx. All rights reserved.
 */

@Module
public abstract class DevMenuModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract DeveloperSettingsFragment developerSettingsFragment();
}
