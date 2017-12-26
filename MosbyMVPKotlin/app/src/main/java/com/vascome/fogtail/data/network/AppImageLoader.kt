package com.vascome.fogtail.data.network

import android.widget.ImageView

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

interface AppImageLoader {
    fun downloadInto(url: String, imageView: ImageView)
}
