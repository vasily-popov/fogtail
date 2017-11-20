package com.vascome.fogtail.models;

import android.support.annotation.NonNull;

import com.vascome.fogtail.api.FogtailRestApi;
import com.vascome.fogtail.api.entities.RecAreaItem;

import java.util.List;

import retrofit2.Call;


/**
 * Model is not an entity. It's a container for business logic code. M(VC), M(VP), M(VVM).
 * <p>
 * Why create Model classes? Such classes hide complex logic required to fetch/cache/process/etc data.
 * So Presentation layer won't know the details of implementation and each class will do only one job (SOLID).
 */

public class RecAreaItemsModel {

    @NonNull
    private final FogtailRestApi restApi;

    public RecAreaItemsModel(@NonNull FogtailRestApi restApi) {
        this.restApi = restApi;
    }

    @NonNull
    public Call<List<RecAreaItem>> getItems() {
        return restApi.items();
    }

}
