package com.vascome.fogtail.presentation.devsettings.views

import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.vascome.fogtail.R
import com.vascome.fogtail.presentation.base.other.ViewModifier

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class MainActivityViewModifier : ViewModifier {

    override fun <T : View> modify(view: T): T {
        // Basically, what we do here is adding a Developer Setting Fragment to a DrawerLayout!
        val drawerLayout = view.findViewById<DrawerLayout>(R.id.main_drawer_layout)

        val layoutParams = DrawerLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        layoutParams.gravity = Gravity.END

        drawerLayout.addView(LayoutInflater.from(view.context).inflate(R.layout.developer_settings_view, drawerLayout, false), layoutParams)
        return view
    }

}
