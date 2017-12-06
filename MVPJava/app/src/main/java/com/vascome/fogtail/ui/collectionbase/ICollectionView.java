package com.vascome.fogtail.ui.collectionbase;

import com.vascome.fogtail.api.entities.RecAreaItem;

import java.util.List;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public interface ICollectionView {

    void setLoadingIndicator(boolean active);

    void showItems(List<RecAreaItem> items);

    void showError();
    
}
