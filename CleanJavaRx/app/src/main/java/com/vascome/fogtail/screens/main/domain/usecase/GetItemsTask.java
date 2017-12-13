package com.vascome.fogtail.screens.main.domain.usecase;

import android.support.annotation.NonNull;

import com.vascome.fogtail.data.gateway.RecItemsDataSource;
import com.vascome.fogtail.screens.base.domain.UseCase;
import com.vascome.fogtail.screens.main.domain.model.RecAreaItem;

import java.util.List;

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public class GetItemsTask extends UseCase<GetItemsTask.RequestValues, GetItemsTask.ResponseValue> {

    private final RecItemsDataSource source;

    public GetItemsTask(RecItemsDataSource source) {
        this.source = source;
    }

    @Override
    protected void executeUseCase(GetItemsTask.RequestValues requestValues) {

        source.getItems(new RecItemsDataSource.LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<RecAreaItem> items) {
                ResponseValue responseValue = new ResponseValue(items);
                getUseCaseCallback().onSuccess(responseValue);
            }

            @Override
            public void onError(Throwable t) {
                getUseCaseCallback().onError(t);
            }

            @Override
            public void onDataNotAvailable() {
                ResponseValue responseValue = new ResponseValue(null);
                getUseCaseCallback().onSuccess(responseValue);
            }
        });


    }


    public static final class RequestValues implements UseCase.RequestValues {

        public RequestValues() {
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<RecAreaItem> items;

        public ResponseValue(@NonNull List<RecAreaItem> items) {
            this.items = items;
        }

        public List<RecAreaItem> getItems() {
            return items;
        }
    }
}
