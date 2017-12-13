package com.vascome.fogtail.screens.main;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.vascome.fogtail.screens.main.domain.model.RecAreaItem;

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

        //void setPresenter(Presenter presenter);
    }

    interface Presenter { //extends BasePresenter {

        void reloadItems();

        void openItemDetail(@NonNull RecAreaItem clickedItem);
    }

    interface Router {

        void openDetailForItem(@NonNull RecAreaItem item);

        void replaceFragment(int content, Fragment fragment);
    }
}