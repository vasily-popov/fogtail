package com.vascome.fogtail.presentation.base.router

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

import com.vascome.fogtail.presentation.base.activity.BaseActivity

/**
 * Created by vasilypopov on 12/12/17
 * Copyright (c) 2017 CleanJava. All rights reserved.
 */

abstract class BaseRouter<out A : BaseActivity>(val activity: A) {
    private val fragmentManager: FragmentManager = activity.supportFragmentManager

    fun replaceFragment(content: Int, fragment: Fragment) {

        fragmentManager.beginTransaction()
                .replace(content, fragment)
                .commit()
    }
}
