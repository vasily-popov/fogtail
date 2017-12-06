package com.vascome.fogtail.ui.collectionbase;

import android.support.annotation.NonNull;

import com.vascome.fogtail.api.entities.RecAreaItem;
import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.models.RecAreaItemsModel;
import com.vascome.fogtail.ui.base.presenters.BasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class CollectionPresenter extends BasePresenter<ICollectionView> {

    private final RecAreaItemsModel itemsModel;
    private final AnalyticsModel analyticsModel;

    public CollectionPresenter(@NonNull RecAreaItemsModel itemsModel,
                              @NonNull AnalyticsModel analyticsModel) {
        this.itemsModel = itemsModel;
        this.analyticsModel = analyticsModel;
    }

    public void reloadData() {

        {
            // Tip: in Kotlin you can use ? to operate with nullable values.
            final ICollectionView view = view();

            if (view != null) {
                view.setLoadingIndicator(true);
            }
        }

        itemsModel.getItems(new Callback<List<RecAreaItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<RecAreaItem>> call, @NonNull Response<List<RecAreaItem>> response) {

                List<RecAreaItem> responseBody = response.body();

                // Tip: in Kotlin you can use ? to operate with nullable values.
                final ICollectionView view = view();
                if (responseBody != null)
                {
                    if (view != null) {
                        view.showItems(responseBody);
                    }
                }
                else {
                    if (view != null) {
                        view.showError();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RecAreaItem>> call, @NonNull Throwable t) {
                analyticsModel.sendError("CollectionPresenter.reloadData failed", t);
                {
                    // Tip: in Kotlin you can use ? to operate with nullable values.
                    final ICollectionView view = view();

                    if (view != null) {
                        view.showError();
                    }
                }
            }
        });
    }
}
