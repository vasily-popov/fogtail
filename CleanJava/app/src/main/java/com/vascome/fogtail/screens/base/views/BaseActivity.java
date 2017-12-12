package com.vascome.fogtail.screens.base.views;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.vascome.fogtail.R;

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    private Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setupToolbar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Nullable
    protected Toolbar toolbar() {
        return toolbar;
    }
}
