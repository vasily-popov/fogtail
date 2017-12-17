package com.vascome.fogtail.presentation.main.fragment.gallery;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding2.view.RxView;
import com.vascome.fogtail.R;
import com.vascome.fogtail.data.network.AppImageLoader;
import com.vascome.fogtail.data.thread.ExecutionScheduler;
import com.vascome.fogtail.databinding.RecyclerRefreshableViewFragmentBinding;
import com.vascome.fogtail.presentation.base.fragments.BaseFragment;
import com.vascome.fogtail.presentation.main.CollectionViewModel;
import com.vascome.fogtail.presentation.main.dto.RecAreaItem;
import com.vascome.fogtail.presentation.main.fragment.gallery.adapter.GalleryAreaAdapter;
import com.vascome.fogtail.presentation.main.fragment.table.decorator.BoxSpaceItemDecoration;
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener;

import javax.inject.Inject;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class GalleryAppFragment extends BaseFragment implements CollectionAreaItemListener {

    RecyclerRefreshableViewFragmentBinding binding;
    GalleryAreaAdapter galleryAreaAdapter;

    @Inject
    Context context;

    @Inject
    CollectionViewModel viewModel;

    @Inject
    AppImageLoader imageLoader;

    @Inject
    ExecutionScheduler scheduler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.recycler_refreshable_view_fragment, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeEvents();
        viewModel.refreshCommand.accept(true);
    }

    private void subscribeEvents() {
        disposables.add(
                viewModel.showProgress()
                        .observeOn(scheduler.UI())
                        .subscribe(RxSwipeRefreshLayout.refreshing(binding.recyclerViewSwipeRefresh)));
        disposables.add(
                RxSwipeRefreshLayout.refreshes(binding.recyclerViewSwipeRefresh)
                        .map(ignored -> true)
                        .subscribe(value -> viewModel.refreshCommand.accept(value)));

        disposables.add(
                viewModel.items()
                        .observeOn(scheduler.UI())
                        .subscribe(recAreaItems -> {
                            if(recAreaItems.size() > 0) {
                                binding.itemsLoadingErrorUi.setVisibility(View.GONE);
                                binding.recyclerViewSwipeRefresh.setVisibility(View.VISIBLE);
                                galleryAreaAdapter.setData(recAreaItems);
                            }
                            else {
                                binding.recyclerViewSwipeRefresh.setVisibility(View.GONE);
                                binding.itemsLoadingErrorUi.setVisibility(View.VISIBLE);
                            }
                        }, throwable -> {
                        }));

        disposables.add(
                RxView.clicks(binding.itemsLoadingErrorTryAgainButton)
                        .subscribe(value -> viewModel.refreshCommand.accept(true)));
    }



    private void initRecyclerView() {

        binding.recyclerView.addItemDecoration(new BoxSpaceItemDecoration((int) getResources().getDimension(R.dimen.list_item_vertical_space_between_items)));
        LinearLayoutManager llm = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerView.setLayoutManager(llm);
        galleryAreaAdapter = new GalleryAreaAdapter(getActivity().getLayoutInflater(), imageLoader, this);
        binding.recyclerView.setAdapter(galleryAreaAdapter);
    }

    @Override
    public void onDestroyView() {
        viewModel.destroy();
        binding.unbind();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(RecAreaItem clickedItem) {
        viewModel.openDetailCommand.accept(clickedItem);
    }

}