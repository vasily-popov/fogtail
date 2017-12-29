package com.vascome.fogtail.di.presentation.devsettings

import com.vascome.fogtail.di.ControllerScope
import com.vascome.fogtail.presentation.devsettings.fragments.DeveloperSettingsFragment

import dagger.Subcomponent

/**
 * Created by vasilypopov on 12/13/17
 * Copyright (c) 2017 CleanJavaRx. All rights reserved.
 */

@Subcomponent
@ControllerScope
interface DevSettingsComponent {

    fun inject(devSettings: DeveloperSettingsFragment)
}