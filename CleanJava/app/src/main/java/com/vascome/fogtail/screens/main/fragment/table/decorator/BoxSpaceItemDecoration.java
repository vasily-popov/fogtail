package com.vascome.fogtail.screens.main.fragment.table.decorator;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class BoxSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int space;

    public BoxSpaceItemDecoration(int dimension) {
        this.space = dimension;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top = space;
        outRect.bottom = space;
        outRect.left = space;
        outRect.right = space;
    }

}
