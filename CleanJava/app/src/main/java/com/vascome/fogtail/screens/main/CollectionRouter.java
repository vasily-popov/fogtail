package com.vascome.fogtail.screens.main;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.vascome.fogtail.screens.base.router.BaseRouter;
import com.vascome.fogtail.screens.detail.RecAreaItemDetailActivity;
import com.vascome.fogtail.screens.main.domain.model.RecAreaItem;


/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

public class CollectionRouter extends BaseRouter<MainActivity> implements CollectionContract.Router {

    public CollectionRouter(MainActivity activity) {
        super(activity);
    }

    @Override
    public void openDetailForItem(@NonNull RecAreaItem item) {
        MainActivity activity = getActivity();
        if(activity != null) {
            Intent intent = new Intent(activity, RecAreaItemDetailActivity.class);
            intent.putExtra("item", item);
            activity.startActivity(intent);
        }
    }
}
