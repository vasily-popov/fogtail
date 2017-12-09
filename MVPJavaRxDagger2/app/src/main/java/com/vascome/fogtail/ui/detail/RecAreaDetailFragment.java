package com.vascome.fogtail.ui.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vascome.fogtail.R;
import com.vascome.fogtail.api.entities.RecAreaItem;
import com.vascome.fogtail.databinding.DetailItemFragmentBinding;
import com.vascome.fogtail.models.AppImageLoader;
import com.vascome.fogtail.ui.base.fragments.BaseFragment;

import javax.inject.Inject;


/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class RecAreaDetailFragment extends BaseFragment {


    private DetailItemFragmentBinding binding;
    private RecAreaItem item;

    @Inject
    AppImageLoader imageLoader;


    public void sendAreaItem(RecAreaItem item) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", item);
        this.setArguments(bundle);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        item = getArguments().getParcelable("item");
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_item_fragment, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initRecyclerView() {

        binding.listItemDescription.setText(item.shortDescription());
        binding.listItemTitle.setText(item.name());
        imageLoader.downloadInto(item.imageUrl(), binding.listItemImageView);
    }

    @Override
    public void onDestroyView() {
        binding.unbind();
        super.onDestroyView();
    }
}
