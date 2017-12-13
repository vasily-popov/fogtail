package com.vascome.fogtail.presentation.main.fragment.stack;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vascome.fogtail.R;
import com.vascome.fogtail.data.network.AppImageLoader;
import com.vascome.fogtail.databinding.StackViewFragmentBinding;
import com.vascome.fogtail.presentation.base.fragments.BaseFragment;
import com.vascome.fogtail.presentation.main.CollectionContract;
import com.vascome.fogtail.presentation.main.CollectionPresenter;
import com.vascome.fogtail.presentation.main.MainActivity;
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem;
import com.vascome.fogtail.presentation.main.fragment.stack.adapter.SwipeStackAdapter;
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener;

import java.util.List;

import javax.inject.Inject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class StackAppFragment extends BaseFragment implements CollectionContract.View, CollectionAreaItemListener {

    SwipeStackAdapter swipeStackAdapter;
    StackViewFragmentBinding binding;

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
        activity.collectionComponent().stackComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.stack_view_fragment, container, false);
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
                binding.itemsLoadingUi.setVisibility(active?VISIBLE:GONE));
    }

    @Override
    public void showItems(List<RecAreaItem> items) {
        runIfFragmentAlive(() -> {
            binding.itemsLoadingErrorUi.setVisibility(GONE);
            binding.swipeStack.setVisibility(VISIBLE);

            if (swipeStackAdapter != null) {
                swipeStackAdapter.setData(items);
            }
        });
    }

    @Override
    public void showError() {

        runIfFragmentAlive(() -> {
            binding.itemsLoadingErrorUi.setVisibility(VISIBLE);
            binding.swipeStack.setVisibility(GONE);
        });

    }

    private void initRecyclerView() {

        swipeStackAdapter = new SwipeStackAdapter(getActivity().getLayoutInflater(), imageLoader, this);
        binding.swipeStack.setAdapter(swipeStackAdapter);

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
