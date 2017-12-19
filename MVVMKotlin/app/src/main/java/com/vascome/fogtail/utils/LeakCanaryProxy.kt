package com.vascome.fogtail.utils

/**
 * Tiny interface to hide LeakCanary from main source set.
 * Yep LeakCanary has it's own no-op impl, but
 * this approach is universal and applicable to any libraries you want to
 * use in debug builds but not in release. Also, this interface is tinier than LeakCanary's no-op one.
 */
interface LeakCanaryProxy {
    fun init()
    fun watch(anyObject: Any)
}

