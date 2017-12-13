package com.vascome.fogtail.data.api;

import android.support.annotation.NonNull;

import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public interface FogtailRestApi {

    @GET("RecArea.json") @NonNull
    Observable<List<RecAreaItem>> items();
}
