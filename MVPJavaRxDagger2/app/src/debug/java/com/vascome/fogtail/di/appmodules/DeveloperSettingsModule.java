package com.vascome.fogtail.di.appmodules;

import android.app.Application;
import android.support.annotation.NonNull;

import com.github.pedrovgs.lynx.LynxConfig;
import com.vascome.fogtail.developer_settings.DeveloperSettings;
import com.vascome.fogtail.developer_settings.DeveloperSettingsModel;
import com.vascome.fogtail.developer_settings.DeveloperSettingsModelImpl;
import com.vascome.fogtail.developer_settings.LeakCanaryProxy;
import com.vascome.fogtail.developer_settings.LeakCanaryProxyImpl;
import com.vascome.fogtail.developer_settings.MainActivityViewModifier;
import com.vascome.fogtail.di.FragmentScope;
import com.vascome.fogtail.di.ui.dev_settings.DevMenuModule;
import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.ui.base.other.ViewModifier;
import com.vascome.fogtail.ui.dev_settings.fragments.DeveloperSettingsFragment;
import com.vascome.fogtail.ui.dev_settings.presenters.DeveloperSettingsPresenter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
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

    // We will use this concrete type for debug code, but main code will see only DeveloperSettingsModel interface.
    @Provides
    @NonNull
    @Singleton
    public DeveloperSettingsModelImpl provideDeveloperSettingsModelImpl(@NonNull Application application,
                                                                        @NonNull DeveloperSettings developerSettings,
                                                                        @NonNull HttpLoggingInterceptor httpLoggingInterceptor,
                                                                        @NonNull LeakCanaryProxy leakCanaryProxy,
                                                                        @NonNull Paperwork paperwork) {
        return new DeveloperSettingsModelImpl(application, developerSettings, httpLoggingInterceptor, leakCanaryProxy, paperwork);
    }

    @NonNull
    @Provides
    public LynxConfig provideLynxConfig() {
        return new LynxConfig();
    }

}