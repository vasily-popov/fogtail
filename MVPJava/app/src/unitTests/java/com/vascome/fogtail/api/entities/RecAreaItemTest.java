package com.vascome.fogtail.api.entities;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class RecAreaItemTest {

    @Test
    public void equals_shouldWorkCorrectly() {
        RecAreaItem itemOne = RecAreaItem.builder().id("id1").imagePreviewUrl("i1").title("Title1").shortDescription("s1").build();
        RecAreaItem itemTwo = RecAreaItem.builder().id("id2").imagePreviewUrl("i2").title("Title2").shortDescription("s2").build();

        assertThat(itemOne).isNotEqualTo(itemTwo);
    }

    @Test
    public void hashcode_shouldWorkCorrectly() {
        RecAreaItem itemOne = RecAreaItem.builder().id("id1").imagePreviewUrl("i1").title("Title1").shortDescription("s1").build();
        RecAreaItem itemOneCopy = RecAreaItem.builder().id("id1").imagePreviewUrl("i1").title("Title1").shortDescription("s1").build();
        RecAreaItem itemTwo = RecAreaItem.builder().id("id2").imagePreviewUrl("i2").title("Title2").shortDescription("s2").build();

        assertThat(itemOne.hashCode()).isEqualTo(itemOneCopy.hashCode());
        assertThat(itemOne.hashCode()).isNotEqualTo(itemTwo.hashCode());
    }
}