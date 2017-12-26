package com.vascome.fogtail.functionalTests

import android.support.test.InstrumentationRegistry

import com.vascome.fogtail.FogtailApplication

class TestUtils private constructor() {

    init {
        throw IllegalStateException("No instances, please")
    }

    companion object {

        fun app(): FogtailApplication {
            return InstrumentationRegistry.getTargetContext().applicationContext as FogtailApplication
        }
    }
}
