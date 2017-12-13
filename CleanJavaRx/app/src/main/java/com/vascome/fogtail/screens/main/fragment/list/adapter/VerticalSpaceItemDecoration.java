package com.vascome.fogtail.screens.main.fragment.list.adapter;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpacePx;

    public VerticalSpaceItemDecoration(int verticalSpacePx) {
        this.verticalSpacePx = verticalSpacePx;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.top = verticalSpacePx;
        outRect.bottom = verticalSpacePx;
    }
}