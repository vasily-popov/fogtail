package com.vascome.fogtail.data.api.entities

import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem

import org.junit.Test

import org.assertj.core.api.Java6Assertions.assertThat

class RecAreaItemTest {

    @Test
    fun equals_shouldWorkCorrectly() {
        val itemOne = RecAreaItem.builder().id("id1").imageUrl("i1").name("Title1").shortDescription("s1")
                .directions("d1").latitude(0.1).longitude(0.1).phone("p1").build()
        val itemTwo = RecAreaItem.builder().id("id2").imageUrl("i2").name("Title2").shortDescription("s2")
                .directions("d2").latitude(0.2).longitude(0.2).phone("p2").build()
        assertThat(itemOne).isNotEqualTo(itemTwo)
    }

    @Test
    fun hashcode_shouldWorkCorrectly() {

        val itemOne = RecAreaItem.builder().id("id1").imageUrl("i1").name("Title1").shortDescription("s1")
                .directions("d1").latitude(0.1).longitude(0.1).phone("p1").build()
        val itemOneCopy = RecAreaItem.builder().id("id1").imageUrl("i1").name("Title1").shortDescription("s1")
                .directions("d1").latitude(0.1).longitude(0.1).phone("p1").build()
        val itemTwo = RecAreaItem.builder().id("id2").imageUrl("i2").name("Title2").shortDescription("s2")
                .directions("d2").latitude(0.2).longitude(0.2).phone("p2").build()

        assertThat(itemOne.hashCode()).isEqualTo(itemOneCopy.hashCode())
        assertThat(itemOne.hashCode()).isNotEqualTo(itemTwo.hashCode())
    }
}