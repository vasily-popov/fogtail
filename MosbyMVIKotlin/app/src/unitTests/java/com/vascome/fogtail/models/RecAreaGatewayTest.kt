package com.vascome.fogtail.models

import com.vascome.fogtail.data.api.FogtailRestApi
import com.vascome.fogtail.data.gateway.RecAreaGateway
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem

import org.junit.Before
import org.junit.Test

import java.util.Arrays.asList
import org.mockito.Mockito.mock

class RecAreaGatewayTest {
    private var qualityMattersRestApi: FogtailRestApi? = null
    private var gateway: RecAreaGateway? = null

    @Before
    fun beforeEachTest() {
        qualityMattersRestApi = mock(FogtailRestApi::class.java)
        gateway = RecAreaGateway(qualityMattersRestApi!!)
    }

    @Test
    fun getItems_shouldReturnItemsFromQualityMattersRestApi() {
        val items = asList(mock(RecAreaItem::class.java), mock(RecAreaItem::class.java))
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