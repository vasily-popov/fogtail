package com.vascome.fogtail.functionalTests.espresso

import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.view.View

import org.hamcrest.Matcher

class ViewActions private constructor() {

    init {
        throw IllegalStateException("No instances please")
    }

    companion object {

        fun noOp(): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View>? {
                    return null
                }

                override fun getDescription(): String {
                    return "no-op"
                }

                override fun perform(uiController: UiController, view: View) {
                    // no-op
                }
            }
        }
    }
}
