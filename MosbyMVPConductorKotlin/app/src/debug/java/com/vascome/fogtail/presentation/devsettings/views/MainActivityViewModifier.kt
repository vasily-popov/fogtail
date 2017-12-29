package com.vascome.fogtail.presentation.devsettings.views

import android.app.Activity
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View

import com.vascome.fogtail.R
import com.vascome.fogtail.presentation.base.other.ViewModifier

import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.vascome.fogtail.presentation.devsettings.fragments.DeveloperSettingsFragment

/**
 * Created by vasilypopov on 11/23/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class MainActivityViewModifier : ViewModifier {

    private lateinit var router: Router

    override fun <T : View> modify(view: T, activity: Activity): T {
        // Basically, what we do here is adding a Developer Setting Fragment to a DrawerLayout!
        val drawerLayout = view.findViewById<DrawerLayout>(R.id.main_drawer_layout)

        val layoutParams = DrawerLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        layoutParams.gravity = Gravity.END

        val container = LayoutInflater.from(view.context).inflate(R.layout.developer_settings_view, drawerLayout, false)
        drawerLayout.addView(container, layoutParams)

        router = Conductor.attachRouter(activity, container.findViewById(R.id.developer_settings), null)
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(DeveloperSettingsFragment()))
        }

        return view
    }

}
