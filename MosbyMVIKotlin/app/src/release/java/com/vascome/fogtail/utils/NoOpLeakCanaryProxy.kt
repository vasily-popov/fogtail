package com.vascome.fogtail.utils

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class NoOpLeakCanaryProxy : LeakCanaryProxy {
    override fun init() {
        // no-op.
    }

    override fun watch(`object`: Any) {
        // no-op.
    }
}
