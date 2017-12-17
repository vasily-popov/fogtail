package com.vascome.fogtail.data.gateway;

import android.support.annotation.NonNull;

import com.vascome.fogtail.data.api.FogtailRestApi;
import com.vascome.fogtail.presentation.main.dto.RecAreaItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public class RecAreaGateway implements  ItemsDataSource {

    @NonNull
    private final FogtailRestApi restApi;

    @Inject
    public RecAreaGateway(@NonNull FogtailRestApi restApi) {
        this.restApi = restApi;
    }


    public Observable<List<RecAreaItem>> getItems() {

        return restApi.items();
    }

}
