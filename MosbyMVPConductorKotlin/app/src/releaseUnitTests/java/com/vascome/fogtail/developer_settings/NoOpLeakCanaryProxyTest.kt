package com.vascome.fogtail.developer_settings

import com.vascome.fogtail.utils.NoOpLeakCanaryProxy

import org.junit.Test

class NoOpLeakCanaryProxyTest {

    @Test
    fun init_shouldNoOp() {
        NoOpLeakCanaryProxy().init() // No exceptions expected.
    }

    @Test
    fun watch_shouldNoOpIfWasNotInit() {
        NoOpLeakCanaryProxy().watch(Any()) // No exceptions expected.
    }

    @Test
    fun watch_shouldNoOpIfWasInit() {
        val leakCanaryProxy = NoOpLeakCanaryProxy()
        leakCanaryProxy.init()
        leakCanaryProxy.watch(Any()) // No exceptions expected.
    }
}