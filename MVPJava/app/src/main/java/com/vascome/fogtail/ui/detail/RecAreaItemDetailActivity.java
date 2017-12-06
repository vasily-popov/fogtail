package com.vascome.fogtail.ui.detail;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.MotionEvent;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vascome.fogtail.R;
import com.vascome.fogtail.api.entities.RecAreaItem;
import com.vascome.fogtail.databinding.ActivityDetailBinding;
import com.vascome.fogtail.ui.base.utils.ActivityUtils;
import com.vascome.fogtail.ui.base.views.BaseActivity;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class RecAreaItemDetailActivity extends BaseActivity {

    private ActivityDetailBinding binding;
    private RecAreaItem item;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            item = extra.getParcelable("item");
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        setContentView(binding.getRoot());
        //allow swipe on map
        binding.transparentImage.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    binding.scrollView.requestDisallowInterceptTouchEvent(true);
                    return false;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    binding.scrollView.requestDisallowInterceptTouchEvent(false);
                    return true;
            }
            return true;
        });

        SupportMapFragment fragment = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map));
        if (fragment != null) {

            fragment.getMapAsync(map ->{
                if(item.latitude() != null && item.longitude() != null) {
                    LatLng latLng = new LatLng(item.latitude(), item.longitude());
                    map.addMarker(new MarkerOptions()
                            .position(latLng)
                            .draggable(false));

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14.0f).build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

                    map.moveCamera(cameraUpdate);
                    map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            });
        }

        RecAreaDetailFragment detailFragment = ((RecAreaDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.card_frame_layout));
        if (detailFragment == null) {

            ActivityUtils.replaceFragmentToActivity(
                    getSupportFragmentManager(), RecAreaDetailFragment.newInstance(item), R.id.card_frame_layout);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}
