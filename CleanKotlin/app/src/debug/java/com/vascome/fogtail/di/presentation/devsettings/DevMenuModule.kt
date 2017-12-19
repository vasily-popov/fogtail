package com.vascome.fogtail.di.presentation.devsettings

import com.vascome.fogtail.di.FragmentScope
import com.vascome.fogtail.presentation.devsettings.fragments.DeveloperSettingsFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by vasilypopov on 12/13/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */

@Module
abstract class DevMenuModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun developerSettingsFragment(): DeveloperSettingsFragment
}
