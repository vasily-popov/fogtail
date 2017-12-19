package com.vascome.fogtail

import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

// Custom runner allows us set config in one place instead of setting it in each test class.
class FogtailIntegrationRobolectricTestRunner @Throws(Exception::class)
constructor(classRun: Class<*>) : RobolectricTestRunner(classRun) {
    companion object {

        private val SDK_EMULATE_LEVEL = 26
        fun fogtailApplication(): FogtailApplication {
            return RuntimeEnvironment.application as FogtailApplication
        }
    }
}
