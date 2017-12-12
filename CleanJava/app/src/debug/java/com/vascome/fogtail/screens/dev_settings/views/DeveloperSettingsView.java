package com.vascome.fogtail.screens.dev_settings.views;

import android.support.annotation.AnyThread;
import android.support.annotation.NonNull;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public interface DeveloperSettingsView {

    @AnyThread
    void changeGitSha(@NonNull String gitSha);

    @AnyThread
    void changeBuildDate(@NonNull String date);

    @AnyThread
    void changeBuildVersionCode(@NonNull String versionCode);

    @AnyThread
    void changeBuildVersionName(@NonNull String versionName);

    @AnyThread
    void changeStethoState(boolean enabled);

    @AnyThread
    void changeLeakCanaryState(boolean enabled);

    @AnyThread
    void changeTinyDancerState(boolean enabled);

    @AnyThread
    void changeHttpLoggingLevel(@NonNull HttpLoggingInterceptor.Level loggingLevel);

    @AnyThread
    void showMessage(@NonNull String message);

    @AnyThread
    void showAppNeedsToBeRestarted();

}
