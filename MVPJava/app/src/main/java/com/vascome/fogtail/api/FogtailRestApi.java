package com.vascome.fogtail.api;

import android.support.annotation.NonNull;

import com.vascome.fogtail.api.entities.RecAreaItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public interface FogtailRestApi {

    @GET("items") @NonNull
    Call<List<RecAreaItem>> items();
}
