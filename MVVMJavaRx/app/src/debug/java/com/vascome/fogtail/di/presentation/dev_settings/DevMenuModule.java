package com.vascome.fogtail.di.presentation.dev_settings;

import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.presentation.dev_settings.fragments.DeveloperSettingsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by vasilypopov on 12/13/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */

@Module
public abstract class DevMenuModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract DeveloperSettingsFragment developerSettingsFragment();
}
