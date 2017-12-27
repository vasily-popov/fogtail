package com.vascome.fogtail.integrationTests.api.entities


import com.google.gson.Gson
import com.vascome.fogtail.BuildConfig
import com.vascome.fogtail.FogtailIntegrationRobolectricTestRunner
import com.vascome.fogtail.FogtailIntegrationTestApp
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

import java.io.IOException

import javax.inject.Inject

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within

@RunWith(FogtailIntegrationRobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(26), application = FogtailIntegrationTestApp::class)
class RecAreaItemTest {

    @Inject
    lateinit var gson: Gson

    @Before
    fun setUp() {
        val component = FogtailIntegrationRobolectricTestRunner.fogtailApplication().appComponent as FogtailIntegrationTestApp.MockAppComponent
        component.inject(this)
    }

    // Why test JSON serialization/deserialization?
    // 1. Update JSON libraries without worrying about breaking changes.
    // 2. Be sure that @JsonIgnore and similar annotations do not affect expected behavior (cc @karlicos).
    @Test
    @Throws(IOException::class)
    fun fromJson() {

        val item = gson.fromJson("{ " +
                "\"RecAreaID\": \"test_id\", " +
                "\"imageURL\": \"some_url\"," +
                "\"RecAreaName\": \"Test title\", " +
                "\"RecAreaPhone\": \"phone\", " +
                "\"RecAreaDirections\": \"directions\", " +
                "\"RecAreaLongitude\": \"0.1\", " +
                "\"RecAreaLatitude\": \"0.2\", " +
                "\"RecAreaDescription\": \"Test short description\"" +
                "}",
                RecAreaItem::class.java)

        assertThat(item.id()).isEqualTo("test_id")
        assertThat(item.imageUrl()).isEqualTo("some_url")
        assertThat(item.name()).isEqualTo("Test title")
        assertThat(item.phone()).isEqualTo("phone")
        assertThat(item.directions()).isEqualTo("directions")
        assertThat(item.latitude()).isCloseTo(0.2, within(0.01))
        assertThat(item.longitude()).isCloseTo(0.1, within(0.01))
        assertThat(item.shortDescription()).isEqualTo("Test short description")
    }
}
