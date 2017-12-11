package com.vascome.fogtail.developer_settings;

import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.utils.LeakCanaryProxy;
import com.vascome.fogtail.utils.LeakCanaryProxyImpl;

import org.junit.Test;

import static org.mockito.Mockito.mock;

public class LeakCanaryProxyImplTest {

    // Unfortunately, we can not really test init method since launching LeakCanary in the tests is not a great idea.

    @Test
    public void watch_shouldNoOpIfInitWasNotCalledCaseWhenLeakCanaryDisabled() {
        LeakCanaryProxy leakCanaryProxy = new LeakCanaryProxyImpl(mock(FogtailApplication.class));
        leakCanaryProxy.watch(new Object()); // No exceptions expected.
    }
}