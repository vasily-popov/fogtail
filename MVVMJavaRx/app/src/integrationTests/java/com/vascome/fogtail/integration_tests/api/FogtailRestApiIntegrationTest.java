package com.vascome.fogtail.integration_tests.api;

import com.vascome.fogtail.BuildConfig;
import com.vascome.fogtail.FogtailIntegrationRobolectricTestRunner;
import com.vascome.fogtail.FogtailIntegrationTestApp;
import com.vascome.fogtail.data.api.FogtailRestApi;
import com.vascome.fogtail.presentation.main.dto.RecAreaItem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.HttpException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Main purpose of Integration tests is to check that all layers of your app work correctly, for example:
 * <ul>
 * <li>Http layer</li>
 * <li>REST layer</li>
 * <li>Parsing/Serializing layer</li>
 * <li>Execution layer (ie RxJava)</li>
 * </ul>
 */
@RunWith(FogtailIntegrationRobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 26, application = FogtailIntegrationTestApp.class)
public class FogtailRestApiIntegrationTest {

    @Inject
    MockWebServer mockWebServer;
    @Inject
    FogtailRestApi fogtailRestApi;

    @Before
    public void setUp() {

        FogtailIntegrationTestApp.MockAppComponent component =
                (FogtailIntegrationTestApp.MockAppComponent)FogtailIntegrationRobolectricTestRunner.fogtailApplication().appComponent;
        component.inject(this);
    }

    @After
    public void shutdown() throws IOException {
        mockWebServer.shutdown();
    }

    /*
    @Test
    public void items_shouldHandleCorrectResponse() {

        mockWebServer.enqueue(new MockResponse().setBody("["
                + "{ \"id\": \"test_id_1\", \"image_preview_url\": \"https://url1\", \"title\": \"Test title 1\", \"short_description\": \"Short desc 1\"},"
                + "{ \"id\": \"test_id_2\", \"image_preview_url\": \"https://url2\", \"title\": \"Test title 2\", \"short_description\": \"Short desc 2\"},"
                + "{ \"id\": \"test_id_3\", \"image_preview_url\": \"https://url3\", \"title\": \"Test title 3\", \"short_description\": \"Short desc 3\"}"
                + "]"));


        // Get items from the API
        List<RecAreaItem> items = fogtailRestApi.items().toBlocking().value();

        assertThat(items).hasSize(3);

        assertThat(items.get(0).id()).isEqualTo("test_id_1");
        assertThat(items.get(0).imagePreviewUrl()).isEqualTo("https://url1");
        assertThat(items.get(0).title()).isEqualTo("Test title 1");
        assertThat(items.get(0).shortDescription()).isEqualTo("Short desc 1");

        assertThat(items.get(1).id()).isEqualTo("test_id_2");
        assertThat(items.get(1).imagePreviewUrl()).isEqualTo("https://url2");
        assertThat(items.get(1).title()).isEqualTo("Test title 2");
        assertThat(items.get(1).shortDescription()).isEqualTo("Short desc 2");

        assertThat(items.get(2).id()).isEqualTo("test_id_3");
        assertThat(items.get(2).imagePreviewUrl()).isEqualTo("https://url3");
        assertThat(items.get(2).title()).isEqualTo("Test title 3");
        assertThat(items.get(2).shortDescription()).isEqualTo("Short desc 3");

    }
    */

    // Such tests assert that no matter how we implement our REST api:
    // Retrofit or not
    // OkHttp or not
    // It should handle error responses too.
    @Test
    public void items_shouldThrowExceptionIfWebServerRespondError() {
        for (Integer errorCode : HttpCodes.clientAndServerSideErrorCodes()) {
            mockWebServer.enqueue(new MockResponse().setStatus("HTTP/1.1 " + errorCode + " Not today"));
            TestObserver<List<RecAreaItem>> testObserver =
                        fogtailRestApi.items().test();
            testObserver.awaitTerminalEvent();
            testObserver.assertError(HttpException.class);
            for(Throwable expected :testObserver.errors()) {
                HttpException httpException = (HttpException) expected;
                assertThat(httpException.code()).isEqualTo(errorCode);
                assertThat(httpException.message()).isEqualTo("Not today");
            }
        }
    }
}
