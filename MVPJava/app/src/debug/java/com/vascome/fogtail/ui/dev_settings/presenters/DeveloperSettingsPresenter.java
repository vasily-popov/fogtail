package com.vascome.fogtail.ui.dev_settings.presenters;

import android.support.annotation.NonNull;

import com.vascome.fogtail.developer_settings.DeveloperSettingsModelImpl;
import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.ui.base.presenters.Presenter;
import com.vascome.fogtail.ui.dev_settings.views.DeveloperSettingsView;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class DeveloperSettingsPresenter extends Presenter<DeveloperSettingsView> {

    @NonNull
    private final DeveloperSettingsModelImpl developerSettingsModel;

    @NonNull
    private final AnalyticsModel analyticsModel;

    public DeveloperSettingsPresenter(@NonNull DeveloperSettingsModelImpl developerSettingsModel, @NonNull AnalyticsModel analyticsModel) {
        this.developerSettingsModel = developerSettingsModel;
        this.analyticsModel = analyticsModel;
    }

    @Override
    public void bindView(@NonNull DeveloperSettingsView view) {
        super.bindView(view);
        syncDeveloperSettings();
    }

    @SuppressWarnings("ConstantConditions")
    public void syncDeveloperSettings() {
        DeveloperSettingsView view = view();

        view.changeGitSha(developerSettingsModel.getGitSha());
        view.changeBuildDate(developerSettingsModel.getBuildDate());
        view.changeBuildVersionCode(developerSettingsModel.getBuildVersionCode());
        view.changeBuildVersionName(developerSettingsModel.getBuildVersionName());
        view.changeStethoState(developerSettingsModel.isStethoEnabled());
        view.changeLeakCanaryState(developerSettingsModel.isLeakCanaryEnabled());
        view.changeTinyDancerState(developerSettingsModel.isTinyDancerEnabled());
        view.changeHttpLoggingLevel(developerSettingsModel.getHttpLoggingLevel());
    }

    public void changeStethoState(boolean enabled) {
        if (developerSettingsModel.isStethoEnabled() == enabled) {
            return; // no-op
        }

        analyticsModel.sendEvent("developer_settings_stetho_" + booleanToEnabledDisabled(enabled));

        boolean stethoWasEnabled = developerSettingsModel.isStethoEnabled();

        developerSettingsModel.changeStethoState(enabled);

        final DeveloperSettingsView view = view();

        if (view != null) {
            view.showMessage("Stetho was " + booleanToEnabledDisabled(enabled));

            if (stethoWasEnabled) {
                view.showAppNeedsToBeRestarted();
            }
        }
    }

    public void changeLeakCanaryState(boolean enabled) {
        if (developerSettingsModel.isLeakCanaryEnabled() == enabled) {
            return; // no-op
        }

        analyticsModel.sendEvent("developer_settings_leak_canary_" + booleanToEnabledDisabled(enabled));

        developerSettingsModel.changeLeakCanaryState(enabled);

        final DeveloperSettingsView view = view();

        if (view != null) {
            view.showMessage("LeakCanary was " + booleanToEnabledDisabled(enabled));
            view.showAppNeedsToBeRestarted(); // LeakCanary can not be enabled on demand (or it's possible?)
        }
    }

    public void changeTinyDancerState(boolean enabled) {
        if (developerSettingsModel.isTinyDancerEnabled() == enabled) {
            return; // no-op
        }

        analyticsModel.sendEvent("developer_settings_tiny_dancer_" + booleanToEnabledDisabled(enabled));

        developerSettingsModel.changeTinyDancerState(enabled);

        final DeveloperSettingsView view = view();

        if (view != null) {
            view.showMessage("TinyDancer was " + booleanToEnabledDisabled(enabled));
        }
    }

    public void changeHttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel) {
        if (developerSettingsModel.getHttpLoggingLevel() == loggingLevel) {
            return; // no-op
        }

        analyticsModel.sendEvent("developer_settings_http_logging_level_" + loggingLevel);

        developerSettingsModel.changeHttpLoggingLevel(loggingLevel);

        final DeveloperSettingsView view = view();

        if (view != null) {
            view.showMessage("Http logging level was changed to " + loggingLevel.toString());
        }
    }

    @NonNull
    private static String booleanToEnabledDisabled(boolean enabled) {
        return enabled ? "enabled" : "disabled";
    }
}

