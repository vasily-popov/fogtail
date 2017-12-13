package com.vascome.fogtail.data.api;

import android.support.annotation.NonNull;

import com.vascome.fogtail.screens.main.domain.model.RecAreaItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public interface FogtailRestApi {

    @GET("RecArea.json") @NonNull
    Call<List<RecAreaItem>> items();
}
