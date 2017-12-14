package com.vascome.fogtail.integration_tests.api.entities;


import com.google.gson.Gson;
import com.vascome.fogtail.BuildConfig;
import com.vascome.fogtail.FogtailIntegrationRobolectricTestRunner;
import com.vascome.fogtail.FogtailIntegrationTestApp;
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.io.IOException;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@RunWith(FogtailIntegrationRobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 26, application = FogtailIntegrationTestApp.class)
public class RecAreaItemTest {

    @Inject
    Gson gson;

    @Before
    public void setUp() {
        FogtailIntegrationTestApp.MockAppComponent component =
                (FogtailIntegrationTestApp.MockAppComponent)FogtailIntegrationRobolectricTestRunner.fogtailApplication().appComponent;
        component.inject(this);
    }

    // Why test JSON serialization/deserialization?
    // 1. Update JSON libraries without worrying about breaking changes.
    // 2. Be sure that @JsonIgnore and similar annotations do not affect expected behavior (cc @karlicos).
    @Test
    public void fromJson() throws IOException {

        RecAreaItem item = gson.fromJson("{ " +
                        "\"RecAreaID\": \"test_id\", " +
                        "\"imageURL\": \"some_url\"," +
                        "\"RecAreaName\": \"Test title\", " +
                        "\"RecAreaPhone\": \"phone\", " +
                        "\"RecAreaDirections\": \"directions\", " +
                        "\"RecAreaLongitude\": \"0.1\", " +
                        "\"RecAreaLatitude\": \"0.2\", " +
                        "\"RecAreaDescription\": \"Test short description\"" +
                        "}",
                RecAreaItem.class);

        assertThat(item.id()).isEqualTo("test_id");
        assertThat(item.imageUrl()).isEqualTo("some_url");
        assertThat(item.name()).isEqualTo("Test title");
        assertThat(item.phone()).isEqualTo("phone");
        assertThat(item.directions()).isEqualTo("directions");
        assertThat(item.latitude()).isCloseTo(0.2,within(0.01));
        assertThat(item.longitude()).isCloseTo(0.1, within(0.01));
        assertThat(item.shortDescription()).isEqualTo("Test short description");
    }
}
