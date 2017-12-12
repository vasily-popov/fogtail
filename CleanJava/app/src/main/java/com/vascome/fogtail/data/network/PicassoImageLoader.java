package com.vascome.fogtail.data.network;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class PicassoImageLoader implements AppImageLoader {

    @NonNull
    private final Picasso picasso;

    public PicassoImageLoader(@NonNull final Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public void downloadInto(@NonNull String url, @NonNull ImageView imageView) {
        picasso.load(url).fit().centerCrop().into(imageView);
    }

}
