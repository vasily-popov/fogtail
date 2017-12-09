package com.vascome.fogtail.api.entities;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RecAreaItemTest {

    @Test
    public void equals_shouldWorkCorrectly() {
        RecAreaItem itemOne = RecAreaItem.builder().id("id1").imageUrl("i1").name("Title1").shortDescription("s1")
                .phone("ph1").directions("d1").latitude(1.0).longitude(.1).build();
        RecAreaItem itemTwo = RecAreaItem.builder().id("id2").imageUrl("i2").name("Title2").shortDescription("s2")
                .phone("ph2").directions("d2").latitude(2.0).longitude(.2).build();

        assertThat(itemOne).isNotEqualTo(itemTwo);
    }

    @Test
    public void hashcode_shouldWorkCorrectly() {
        RecAreaItem itemOne = RecAreaItem.builder().id("id1").imageUrl("i1").name("Title1").shortDescription("s1")
                .phone("ph1").directions("d1").latitude(1.0).longitude(.1).build();
        RecAreaItem itemOneCopy = RecAreaItem.builder().id("id1").imageUrl("i1").name("Title1").shortDescription("s1")
                .phone("ph1").directions("d1").latitude(1.0).longitude(.1).build();
        RecAreaItem itemTwo = RecAreaItem.builder().id("id2").imageUrl("i2").name("Title2").shortDescription("s2")
                .phone("ph2").directions("d2").latitude(2.0).longitude(.2).build();

        assertThat(itemOne.hashCode()).isEqualTo(itemOneCopy.hashCode());
        assertThat(itemOne.hashCode()).isNotEqualTo(itemTwo.hashCode());
    }
}