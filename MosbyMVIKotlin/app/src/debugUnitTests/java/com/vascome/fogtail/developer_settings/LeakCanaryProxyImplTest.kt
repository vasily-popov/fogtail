package com.vascome.fogtail.developer_settings

import com.vascome.fogtail.FogtailApplication
import com.vascome.fogtail.utils.LeakCanaryProxy
import com.vascome.fogtail.utils.LeakCanaryProxyImpl

import org.junit.Test

import org.mockito.Mockito.mock

@Suppress("FunctionName")
class LeakCanaryProxyImplTest {

    // Unfortunately, we can not really test init method since launching LeakCanary in the tests is not a great idea.

    @Test
    fun watch_shouldNoOpIfInitWasNotCalledCaseWhenLeakCanaryDisabled() {
        val leakCanaryProxy = LeakCanaryProxyImpl(mock(FogtailApplication::class.java))
        leakCanaryProxy.watch(Any()) // No exceptions expected.
    }
}