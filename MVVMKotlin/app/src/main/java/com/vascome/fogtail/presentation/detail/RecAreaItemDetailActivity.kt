package com.vascome.fogtail.presentation.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vascome.fogtail.R
import com.vascome.fogtail.presentation.base.activity.BaseActivity
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import kotlinx.android.synthetic.main.activity_detail.*

import javax.inject.Inject

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class RecAreaItemDetailActivity : BaseActivity(), View.OnTouchListener {

    private lateinit var item: RecAreaItem

    @Inject
    lateinit var router: DetailRouter

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extra = intent.extras
        if (extra != null) {
            item = extra.getParcelable("item")
        }

        setContentView(layoutInflater.inflate(R.layout.activity_detail, null))

        transparentImage.setOnTouchListener(this)

        val fragment = supportFragmentManager
                .findFragmentById(R.id.map) as? SupportMapFragment
        fragment?.getMapAsync { map ->
            if (item.latitude() != null && item.longitude() != null) {
                val latLng = LatLng(item.latitude()!!, item.longitude()!!)
                map.addMarker(MarkerOptions()
                        .position(latLng)
                        .draggable(false))

                val cameraPosition = CameraPosition.Builder().target(latLng).zoom(14.0f).build()
                val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)

                map.moveCamera(cameraUpdate)
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            }
        }

        val detailFragment = supportFragmentManager
                .findFragmentById(R.id.card_frame_layout) as? RecAreaDetailFragment
        if (detailFragment == null) {
            router.replaceFragment(R.id.card_frame_layout, RecAreaDetailFragment.newInstance(item))
        }

    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                scrollView.requestDisallowInterceptTouchEvent(true)
                return false
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                scrollView.requestDisallowInterceptTouchEvent(false)
                return true
            }
        }
        return true
    }
}
