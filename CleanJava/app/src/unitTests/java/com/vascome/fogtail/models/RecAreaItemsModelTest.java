package com.vascome.fogtail.models;

import com.vascome.fogtail.api.FogtailRestApi;
import com.vascome.fogtail.api.entities.RecAreaItem;
import com.vascome.fogtail.screens.main.RecAreaItemsModel;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;

public class RecAreaItemsModelTest {
    private FogtailRestApi qualityMattersRestApi;
    private RecAreaItemsModel itemsModel;

    @Before
    public void beforeEachTest() {
        qualityMattersRestApi = mock(FogtailRestApi.class);
        itemsModel = new RecAreaItemsModel(qualityMattersRestApi);
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