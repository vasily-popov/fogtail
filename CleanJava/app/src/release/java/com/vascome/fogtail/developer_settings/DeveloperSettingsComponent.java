package com.vascome.fogtail.developer_settings;

import dagger.Subcomponent;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Subcomponent
public interface DeveloperSettingsComponent {
    // Hides details of developer settings in the release build type.

    @Subcomponent.Builder
    interface Builder {
        DeveloperSettingsComponent build();
    }
}
