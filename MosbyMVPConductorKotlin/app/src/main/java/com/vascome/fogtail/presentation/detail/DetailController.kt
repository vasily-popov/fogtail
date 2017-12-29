package com.vascome.fogtail.presentation.detail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ScrollView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vascome.fogtail.R

import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.databinding.DetailItemFragmentBinding
import com.vascome.fogtail.presentation.MainActivity
import com.vascome.fogtail.presentation.base.controllers.BaseViewController
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem

import javax.inject.Inject
import android.view.InflateException
import com.vascome.fogtail.databinding.ActivityDetailBinding


@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class DetailController(args: Bundle) :
        BaseViewController<DetailContract.View, DetailContract.Presenter, DetailViewState>(),
        DetailContract.View,
        View.OnTouchListener {

    override fun createViewState() = DetailViewState()

    override fun onNewViewStateInstance() {
    }

    private var item: RecAreaItem? = null

    @Inject
    lateinit var imageLoader: AppImageLoader

    @Inject
    lateinit var detailPresenter: DetailPresenter

    override fun createPresenter(): DetailContract.Presenter = detailPresenter

    lateinit private var bindingCard: DetailItemFragmentBinding
    lateinit private var scrollView :ScrollView
    lateinit private var binding: ActivityDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        if(!isInjected) {
            (activity as MainActivity).collectionComponent().detailComponent().inject(this)
            isInjected = true
        }

        binding = DataBindingUtil.inflate(inflater,R.layout.activity_detail, container, false)
        bindingCard = DataBindingUtil.inflate(inflater, R.layout.detail_item_fragment, binding.cardFrameLayout, true)
        initView(binding.root)
        return binding.root
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        val fragment = activity!!.fragmentManager
                .findFragmentById(R.id.map) as? MapFragment
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
    }

    private fun initView(view: View) {

        val transparentImage = view.findViewById<ImageView>(R.id.transparentImage)
        scrollView = view.findViewById(R.id.scrollView)
        transparentImage.setOnTouchListener(this)
        bindingCard.listItemDescription.text = item?.shortDescription()
        bindingCard.listItemTitle.text= item?.name()
        item?.imageUrl()?.let { url -> imageLoader.downloadInto(url,bindingCard.listItemImageView)  }
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE -> {
                scrollView.requestDisallowInterceptTouchEvent(true)
                return false
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                scrollView.requestDisallowInterceptTouchEvent(false)
                view?.performClick()
                return true
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        val fragment = activity!!.fragmentManager
                .findFragmentById(R.id.map) as? MapFragment
        if (fragment != null) {
            activity!!.fragmentManager
                    .beginTransaction().remove(fragment).commit()
        }
    }


    companion object {
        fun newInstance(item: RecAreaItem?): DetailController {
            val bundle = Bundle()
            bundle.putParcelable("item", item)
            return DetailController(bundle)
        }
    }

    init {
        item = args.getParcelable("item")
    }
}
