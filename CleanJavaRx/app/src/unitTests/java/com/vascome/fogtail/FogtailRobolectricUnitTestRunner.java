package com.vascome.fogtail;

import android.support.annotation.NonNull;

import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

// Custom runner allows us set config in one place instead of setting it in each test class.
public class FogtailRobolectricUnitTestRunner extends RobolectricTestRunner {

    // This value should be changed as soon as Robolectric will support newer api.
    private static final int SDK_EMULATE_LEVEL = 23;

    public FogtailRobolectricUnitTestRunner(@NonNull Class<?> classRun) throws Exception {
        super(classRun);
    }

    @NonNull
    public static FogtailApplication fogtailApplication() {
        return (FogtailApplication) RuntimeEnvironment.application;
    }
}
