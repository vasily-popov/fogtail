package com.vascome.fogtail.presentation.detail;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.vascome.fogtail.R;
import com.vascome.fogtail.data.network.AppImageLoader;
import com.vascome.fogtail.data.thread.ExecutionScheduler;
import com.vascome.fogtail.databinding.DetailItemFragmentBinding;
import com.vascome.fogtail.presentation.base.fragments.BaseFragment;
import com.vascome.fogtail.presentation.main.dto.RecAreaItem;

import javax.inject.Inject;


/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class RecAreaDetailFragment extends BaseFragment {


    private DetailItemFragmentBinding binding;

    @Inject
    AppImageLoader imageLoader;

    @Inject
    DetailViewModel viewModel;

    @Inject
    ExecutionScheduler scheduler;

    public static RecAreaDetailFragment newInstance(RecAreaItem item) {

        RecAreaDetailFragment fragment = new RecAreaDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("item", item);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RecAreaItem item = getArguments().getParcelable("item");
        if (item != null) {
            viewModel.setItemCommand.accept(item);
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_item_fragment, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeEvents();
    }

    private void subscribeEvents() {

        disposables.add(
                viewModel.itemDescription()
                        .observeOn(scheduler.UI())
                        .subscribe(RxTextView.text(binding.listItemDescription))
        );

        disposables.add(
                viewModel.name()
                        .observeOn(scheduler.UI())
                        .subscribe(RxTextView.text(binding.listItemTitle))
        );

        disposables.add(
                viewModel.imageUrl()
                        .observeOn(scheduler.UI())
                        .subscribe(url -> imageLoader.downloadInto(url, binding.listItemImageView))
        );
    }

    @Override
    public void onDestroyView() {
        viewModel.destroy();
        binding.unbind();
        super.onDestroyView();
    }
}
