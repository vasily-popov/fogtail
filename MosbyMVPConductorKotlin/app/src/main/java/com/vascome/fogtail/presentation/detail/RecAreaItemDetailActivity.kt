package com.vascome.fogtail.presentation.detail

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MotionEvent
import android.view.View

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vascome.fogtail.R
import com.vascome.fogtail.databinding.ActivityDetailBinding
import com.vascome.fogtail.presentation.base.router.BaseRouter
import com.vascome.fogtail.presentation.base.views.BaseActivity
import com.vascome.fogtail.presentation.base.views.RightSlidingActivity
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem

import javax.inject.Inject

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class RecAreaItemDetailActivity : RightSlidingActivity(), View.OnTouchListener {

    private lateinit var binding: ActivityDetailBinding
    private var item: RecAreaItem? = null

    @Inject
    lateinit var router: DetailRouter

    override fun getRootView(): View = binding.root

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        item = intent.extras?.getParcelable("item")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        setContentView(binding.root)
        //allow swipe on map
        binding.transparentImage.setOnTouchListener(this)

        val fragment = supportFragmentManager
                .findFragmentById(R.id.map) as? SupportMapFragment
        fragment?.getMapAsync { map ->
            if (item?.latitude() != null && item?.longitude() != null) {
                val latLng = LatLng(item!!.latitude()!!, item!!.longitude()!!)
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


    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                binding.scrollView.requestDisallowInterceptTouchEvent(true)
                return false
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                binding.scrollView.requestDisallowInterceptTouchEvent(false)
                view?.performClick()
                return true
            }
        }
        return true
    }
}
