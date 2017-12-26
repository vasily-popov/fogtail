package com.vascome.fogtail.presentation.base.views

import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup

import com.vascome.fogtail.R

import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by vasilypopov on 11/22/17
 * Copyright (c) 2017 fogtail. All rights reserved.
 */

abstract class BaseActivity : DaggerAppCompatActivity() {

    private var toolbar: Toolbar? = null

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setupToolbar()
    }

    override fun setContentView(view: View) {
        super.setContentView(view)
        setupToolbar()
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        super.setContentView(view, params)
        setupToolbar()
    }

    private fun setupToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    protected fun toolbar(): Toolbar? {
        return toolbar
    }
}
