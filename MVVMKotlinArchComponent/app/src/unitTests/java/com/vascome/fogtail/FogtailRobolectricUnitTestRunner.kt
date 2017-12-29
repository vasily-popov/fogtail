package com.vascome.fogtail

import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

// Custom runner allows us set config in one place instead of setting it in each test class.
class FogtailRobolectricUnitTestRunner @Throws(Exception::class)
constructor(classRun: Class<*>) : RobolectricTestRunner(classRun) {
    companion object {

        // This value should be changed as soon as Robolectric will support newer api.
        private val SDK_EMULATE_LEVEL = 23

        fun fogtailApplication(): FogtailApplication {
            return RuntimeEnvironment.application as FogtailApplication
        }
    }
}
