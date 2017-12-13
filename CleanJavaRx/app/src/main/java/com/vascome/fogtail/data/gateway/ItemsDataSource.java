package com.vascome.fogtail.data.gateway;

import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem;

import java.util.List;
import io.reactivex.Observable;


/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public interface ItemsDataSource {

    Observable<List<RecAreaItem>> getItems();

}
