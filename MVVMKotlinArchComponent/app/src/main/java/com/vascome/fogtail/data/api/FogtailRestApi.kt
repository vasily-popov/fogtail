package com.vascome.fogtail.data.api

import com.vascome.fogtail.presentation.main.dto.RecAreaItem

import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

interface FogtailRestApi {

    @GET("RecArea.json")
    fun items(): Single<List<RecAreaItem>>
}
