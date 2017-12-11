package com.vascome.fogtail.ui.main.collectionbase;

import android.support.annotation.NonNull;

import com.vascome.fogtail.api.entities.RecAreaItem;

import java.util.List;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public interface CollectionContract {

    interface View {

        void setLoadingIndicator(boolean active);

        void showItems(List<RecAreaItem> items);

        void showError();

        void openItemDetail(@NonNull RecAreaItem clickedItem);

        //void setPresenter(Presenter presenter);
    }

    interface Presenter { //extends BasePresenter {

        void reloadItems();

        void openItemDetail(@NonNull RecAreaItem clickedItem);
    }
}