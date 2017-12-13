package com.vascome.fogtail.presentation.main.fragment.table;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vascome.fogtail.R;
import com.vascome.fogtail.data.network.AppImageLoader;
import com.vascome.fogtail.databinding.RecyclerRefreshableViewFragmentBinding;
import com.vascome.fogtail.presentation.base.fragments.BaseFragment;
import com.vascome.fogtail.presentation.main.CollectionContract;
import com.vascome.fogtail.presentation.main.CollectionPresenter;
import com.vascome.fogtail.presentation.main.MainActivity;
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem;
import com.vascome.fogtail.presentation.main.fragment.list.adapter.ListAreaAdapter;
import com.vascome.fogtail.presentation.main.fragment.table.decorator.BoxSpaceItemDecoration;
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener;

import java.util.List;

import javax.inject.Inject;


/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class GridAppFragment extends BaseFragment implements CollectionContract.View, CollectionAreaItemListener {

    RecyclerRefreshableViewFragmentBinding binding;
    ListAreaAdapter listAreaAdapter;

    @Inject
    Context context;
    @Inject
    CollectionPresenter presenter;

    @Inject
    AppImageLoader imageLoader;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        MainActivity activity = (MainActivity) getActivity();
        activity.collectionComponent().gridComponent().inject(this);
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
        presenter.resume();
        presenter.reloadItems();
    }

    @Override
    public void setLoadingIndicator(boolean active) {

        runIfFragmentAlive(() ->
                binding.recyclerViewSwipeRefresh.post(() ->
                        binding.recyclerViewSwipeRefresh.setRefreshing(active)));
    }

    @Override
    public void showItems(List<RecAreaItem> items) {
        runIfFragmentAlive(() -> {
            binding.itemsLoadingErrorUi.setVisibility(View.GONE);
            binding.recyclerViewSwipeRefresh.setVisibility(View.VISIBLE);
            if (listAreaAdapter != null) {
                listAreaAdapter.setData(items);
            }
        });
    }

    @Override
    public void showError() {

        runIfFragmentAlive(() -> {
            binding.recyclerViewSwipeRefresh.setVisibility(View.GONE);
            binding.itemsLoadingErrorUi.setVisibility(View.VISIBLE);
        });

    }

    private void initRecyclerView() {

        binding.recyclerView.addItemDecoration(new BoxSpaceItemDecoration((int) getResources().getDimension(R.dimen.list_item_vertical_space_between_items)));
        GridLayoutManager glm = new GridLayoutManager(context, 2);
        binding.recyclerView.setLayoutManager(glm);
        listAreaAdapter = new ListAreaAdapter(getActivity().getLayoutInflater(), imageLoader, this);
        binding.recyclerView.setAdapter(listAreaAdapter);
        binding.recyclerViewSwipeRefresh.setOnRefreshListener(()->presenter.reloadItems());

        presenter.bindView(this);
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        presenter.destroy();
        binding.unbind();
        super.onDestroyView();
    }

    @Override
    public void onItemClick(RecAreaItem clickedItem) {
        presenter.openItemDetail(clickedItem);
    }
}