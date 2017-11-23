package com.vascome.fogtail.functional_tests;

import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

import com.vascome.fogtail.FogtailApplication;

public class TestUtils {

    private TestUtils() {
        throw new IllegalStateException("No instances, please");
    }

    @NonNull
    public static FogtailApplication app() {
        return (FogtailApplication) InstrumentationRegistry.getTargetContext().getApplicationContext();
    }
}
