package com.vascome.fogtail.data.gateway;

import android.support.annotation.NonNull;

import com.vascome.fogtail.screens.main.domain.model.RecAreaItem;

import java.util.List;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public interface RecItemsDataSource {

    interface LoadItemsCallback {

        void onItemsLoaded(List<RecAreaItem> items);
        void onError(Throwable t);
        void onDataNotAvailable();
    }

    void getItems(@NonNull LoadItemsCallback callback);

}
