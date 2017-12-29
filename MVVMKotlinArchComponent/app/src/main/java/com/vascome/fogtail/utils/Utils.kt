package com.vascome.fogtail.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

/**
 * Created by vasilypopov on 12/29/17
 * Copyright (c) 2017 MVVMKotlinArchComponent. All rights reserved.
 *
 *
 */

fun replaceFragment(activity: AppCompatActivity, content: Int, fragment: Fragment) {

    val fragmentManager: FragmentManager = activity.supportFragmentManager
    fragmentManager.beginTransaction()
            .replace(content, fragment)
            .commit()
}