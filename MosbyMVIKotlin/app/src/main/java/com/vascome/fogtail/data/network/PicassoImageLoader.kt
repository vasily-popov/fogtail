package com.vascome.fogtail.data.network

import android.widget.ImageView

import com.squareup.picasso.Picasso

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class PicassoImageLoader(private val picasso: Picasso) : AppImageLoader {

    override fun downloadInto(url: String, imageView: ImageView) {
        picasso.load(url)
                .fit()
                .centerCrop()
                .into(imageView)
    }

}
