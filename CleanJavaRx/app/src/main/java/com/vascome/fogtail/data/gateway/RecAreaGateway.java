package com.vascome.fogtail.data.gateway;

import android.support.annotation.NonNull;

import com.vascome.fogtail.data.api.FogtailRestApi;
import com.vascome.fogtail.screens.main.domain.model.RecAreaItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public class RecAreaGateway implements  RecItemsDataSource {

    @NonNull
    private final FogtailRestApi restApi;

    public RecAreaGateway(@NonNull FogtailRestApi restApi) {
        this.restApi = restApi;
    }

    public void getItems(@NonNull final LoadItemsCallback callback) {
        restApi.items().enqueue(new Callback<List<RecAreaItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<RecAreaItem>> call, @NonNull Response<List<RecAreaItem>> response) {

                List<RecAreaItem> responseBody = response.body();
                if(responseBody != null && responseBody.size() > 0) {
                    callback.onItemsLoaded(responseBody);
                }
                else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RecAreaItem>> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

}
