package com.vascome.fogtail.data.gateway

import com.vascome.fogtail.data.api.FogtailRestApi
import com.vascome.fogtail.presentation.main.dto.RecAreaItem

import javax.inject.Inject

import io.reactivex.Single

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

class RecAreaGateway
@Inject constructor(private val restApi: FogtailRestApi) : ItemsDataSource {


    override val items: Single<List<RecAreaItem>>
        get() = restApi.items()

}
