package com.vascome.fogtail.integration_tests.api.entities;

import com.google.gson.Gson;
import com.vascome.fogtail.FogtailIntegrationRobolectricTestRunner;
import com.vascome.fogtail.api.entities.RecAreaItem;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(FogtailIntegrationRobolectricTestRunner.class)
public class RecAreaItemTest {

    // Why test JSON serialization/deserialization?
    // 1. Update JSON libraries without worrying about breaking changes.
    // 2. Be sure that @JsonIgnore and similar annotations do not affect expected behavior (cc @karlicos).
    @Test
    public void fromJson() throws IOException {
        Gson gson = FogtailIntegrationRobolectricTestRunner.fogtailApplication().applicationComponent().gson();

        RecAreaItem item = gson.fromJson("{ " +
                        "\"id\": \"test_id\", " +
                        "\"image_preview_url\": \"some_url\"," +
                        "\"title\": \"Test title\", " +
                        "\"short_description\": \"Test short description\"" +
                        "}",
                RecAreaItem.class);

        assertThat(item.id()).isEqualTo("test_id");
        assertThat(item.imagePreviewUrl()).isEqualTo("some_url");
        assertThat(item.title()).isEqualTo("Test title");
        assertThat(item.shortDescription()).isEqualTo("Test short description");
    }
}
