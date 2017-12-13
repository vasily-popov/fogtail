package com.vascome.fogtail;

import android.support.annotation.NonNull;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

// Custom runner allows us set config in one place instead of setting it in each test class.
public class FogtailIntegrationRobolectricTestRunner extends RobolectricTestRunner {

    private static final int SDK_EMULATE_LEVEL = 26;

    public FogtailIntegrationRobolectricTestRunner(@NonNull Class<?> classRun) throws Exception {
        super(classRun);
    }
    @NonNull
    public static FogtailApplication fogtailApplication() {
        return (FogtailApplication) RuntimeEnvironment.application;
    }
}
