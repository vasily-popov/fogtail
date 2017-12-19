package com.vascome.fogtail.data.gateway

import com.vascome.fogtail.presentation.main.dto.RecAreaItem

import io.reactivex.Observable


/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

interface ItemsDataSource {

    val items: Observable<List<RecAreaItem>>
}
