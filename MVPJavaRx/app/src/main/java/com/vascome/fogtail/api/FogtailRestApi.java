package com.vascome.fogtail.api;

import android.support.annotation.NonNull;

import com.vascome.fogtail.api.entities.RecAreaItem;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public interface FogtailRestApi {

    @GET("RecArea.json") @NonNull
    Single<List<RecAreaItem>> items();
}
