package com.vascome.fogtail.data.network;

import android.support.annotation.NonNull;
import android.widget.ImageView;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public interface AppImageLoader {
    void downloadInto(@NonNull String url, @NonNull ImageView imageView);
}
