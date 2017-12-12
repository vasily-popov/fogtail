package com.vascome.fogtail.di.appmodules;

import android.support.annotation.NonNull;

import com.vascome.fogtail.screens.base.other.NoOpViewModifier;
import com.vascome.fogtail.screens.base.other.ViewModifier;
import com.vascome.fogtail.screens.dev_settings.DeveloperSettingsModel;
import com.vascome.fogtail.utils.LeakCanaryProxy;
import com.vascome.fogtail.utils.NoOpLeakCanaryProxy;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module
public class DeveloperSettingsModule {

    @NonNull
    public static final String MAIN_ACTIVITY_VIEW_MODIFIER = "main_activity_view_modifier";

    @Provides
    @NonNull @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    public ViewModifier provideMainActivityViewModifier() {
        return new NoOpViewModifier();
    }

    @Provides
    @NonNull
    public DeveloperSettingsModel provideDeveloperSettingsModel() {
        return () -> {
            // no-op!
        };
    }

    @Provides
    @NonNull
    @Singleton
    public LeakCanaryProxy provideLeakCanaryProxy() {
        return new NoOpLeakCanaryProxy();
    }
}