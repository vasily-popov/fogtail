package com.vascome.fogtail.functionalTests.espresso

import android.support.test.espresso.ViewAssertion
import android.support.v7.widget.RecyclerView

import com.vascome.fogtail.presentation.main.CollectionContract

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
