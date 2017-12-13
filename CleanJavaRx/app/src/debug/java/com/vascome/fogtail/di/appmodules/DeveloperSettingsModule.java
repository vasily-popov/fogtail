package com.vascome.fogtail.di.appmodules;

import android.app.Application;
import android.support.annotation.NonNull;

import com.github.pedrovgs.lynx.LynxConfig;
import com.vascome.fogtail.di.presentation.dev_settings.DevMenuModule;
import com.vascome.fogtail.presentation.base.other.ViewModifier;
import com.vascome.fogtail.presentation.dev_settings.DeveloperSettingsModel;
import com.vascome.fogtail.presentation.dev_settings.model.DeveloperSettings;
import com.vascome.fogtail.presentation.dev_settings.model.DeveloperSettingsModelImpl;
import com.vascome.fogtail.presentation.dev_settings.presenters.DeveloperSettingsPresenter;
import com.vascome.fogtail.presentation.dev_settings.views.MainActivityViewModifier;
import com.vascome.fogtail.utils.AnalyticsModel;
import com.vascome.fogtail.utils.LeakCanaryProxy;
import com.vascome.fogtail.utils.LeakCanaryProxyImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.supercluster.paperwork.Paperwork;
import okhttp3.logging.HttpLoggingInterceptor;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

@Module(includes = DevMenuModule.class)
public class DeveloperSettingsModule {

    @NonNull
    public static final String MAIN_ACTIVITY_VIEW_MODIFIER = "main_activity_view_modifier";

    @Provides
    @NonNull
    @Named(MAIN_ACTIVITY_VIEW_MODIFIER)
    public ViewModifier provideMainActivityViewModifier() {
        return new MainActivityViewModifier();
    }

    @Provides
    @NonNull
    public DeveloperSettingsModel provideDeveloperSettingsModel(@NonNull DeveloperSettingsModelImpl developerSettingsModelImpl) {
        return developerSettingsModelImpl;
    }

    @Provides
    @NonNull
    @Singleton
    public DeveloperSettings provideDeveloperSettings(@NonNull Application application) {
        return new DeveloperSettings(application.getSharedPreferences("developer_settings", MODE_PRIVATE));
    }

    @Provides
    @NonNull
    @Singleton
    public LeakCanaryProxy provideLeakCanaryProxy(@NonNull Application application) {
        return new LeakCanaryProxyImpl(application);
    }

    @Provides
    @NonNull
    @Singleton
    public Paperwork providePaperwork(@NonNull Application application) {
        return new Paperwork(application);
    }

    @NonNull
    @Provides
    public LynxConfig provideLynxConfig() {
        return new LynxConfig();
    }

}