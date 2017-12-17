package com.vascome.fogtail.models;

import com.vascome.fogtail.data.api.FogtailRestApi;
import com.vascome.fogtail.data.gateway.RecAreaGateway;
import com.vascome.fogtail.presentation.main.dto.RecAreaItem;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;

public class RecAreaGatewayTest {
    private FogtailRestApi qualityMattersRestApi;
    private RecAreaGateway gateway;

    @Before
    public void beforeEachTest() {
        qualityMattersRestApi = mock(FogtailRestApi.class);
        gateway = new RecAreaGateway(qualityMattersRestApi);
    }

    @Test
    public void getItems_shouldReturnItemsFromQualityMattersRestApi() {
        List<RecAreaItem> items = asList(mock(RecAreaItem.class), mock(RecAreaItem.class));
        //when(qualityMattersRestApi.items()).thenReturn(Single.just(items));

        //assertThat(itemsModel.getItems().toBlocking().value()).containsExactlyElementsOf(items);
    }
/*
    @Test
    public void getItems_shouldReturnErrorFromQualityMattersRestApi() {
        Exception error = new RuntimeException();
        when(qualityMattersRestApi.items()).thenReturn(Single.error(error));

        try {
            itemsModel.getItems().toBlocking().value();
            failBecauseExceptionWasNotThrown(RuntimeException.class);
        } catch (Exception expected) {
            assertThat(expected).isSameAs(error);
        }
    }
    */
}