package com.vascome.fogtail.developer_settings;

import android.support.annotation.NonNull;

import com.vascome.fogtail.ui.dev_settings.fragments.DeveloperSettingsFragment;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Subcomponent
public interface DeveloperSettingsComponent {
    void inject(@NonNull DeveloperSettingsFragment developerSettingsFragment);
}