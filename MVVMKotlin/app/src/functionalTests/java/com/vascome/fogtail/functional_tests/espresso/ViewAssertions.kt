package com.vascome.fogtail.functional_tests.espresso

import android.support.test.espresso.ViewAssertion
import android.support.v7.widget.RecyclerView

class ViewAssertions private constructor() {

    init {
        throw IllegalStateException("No instances please")
    }

    companion object {

        fun recyclerViewShouldHaveItemsCount(count: Int): ViewAssertion {
            return ViewAssertion { view, _ ->
                val recyclerView = view as RecyclerView
                val actualCount = recyclerView.adapter.itemCount

                if (actualCount != count) {
                    throw AssertionError("RecyclerView has $actualCount while expected $count")
                }
            }
        }
    }
}
