package com.vascome.fogtail.presentation.main

import android.content.Intent

import com.vascome.fogtail.presentation.base.router.BaseRouter
import com.vascome.fogtail.presentation.detail.RecAreaItemDetailActivity
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem

import javax.inject.Inject


/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

class CollectionRouter
@Inject constructor(activity: MainActivity)
    : BaseRouter<MainActivity>(activity) {

     fun openDetailForItem(item: RecAreaItem) {
         val intent = Intent(activity, RecAreaItemDetailActivity::class.java)
         intent.putExtra("item", item)
         activity.startActivity(intent)
     }
}
