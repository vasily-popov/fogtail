package com.vascome.fogtail.presentation.main.fragment.stack;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.view.RxView;
import com.vascome.fogtail.R;
import com.vascome.fogtail.data.network.AppImageLoader;
import com.vascome.fogtail.data.thread.ExecutionScheduler;
import com.vascome.fogtail.databinding.StackViewFragmentBinding;
import com.vascome.fogtail.presentation.base.fragments.BaseFragment;
import com.vascome.fogtail.presentation.main.CollectionViewModel;
import com.vascome.fogtail.presentation.main.dto.RecAreaItem;
import com.vascome.fogtail.presentation.main.fragment.stack.adapter.SwipeStackAdapter;
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener;

import javax.inject.Inject;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class StackAppFragment extends BaseFragment implements CollectionAreaItemListener {

    SwipeStackAdapter swipeStackAdapter;
    StackViewFragmentBinding binding;

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
        subscribeEvents();
        viewModel.refreshCommand.accept(true);
    }

    private void subscribeEvents() {
        disposables.add(
                viewModel.showProgress()
                        .observeOn(scheduler.UI())
                        .subscribe(value -> binding.itemsLoadingUi.setVisibility(value?VISIBLE:GONE))
        );

        disposables.add(
                viewModel.items()
                        .observeOn(scheduler.UI())
                        .subscribe(recAreaItems -> {

                            if(recAreaItems.size() > 0) {
                                binding.itemsLoadingErrorUi.setVisibility(View.GONE);
                                binding.swipeStack.setVisibility(View.VISIBLE);
                                swipeStackAdapter.setData(recAreaItems);
                            }
                            else {
                                binding.swipeStack.setVisibility(View.GONE);
                                binding.itemsLoadingErrorUi.setVisibility(View.VISIBLE);
                            }
                        }, throwable -> {
                        }));

        disposables.add(
                RxView.clicks(binding.itemsLoadingErrorTryAgainButton)
                        .subscribe(value -> {
                            viewModel.refreshCommand.accept(true);
                        }));
    }

    private void initRecyclerView() {
        swipeStackAdapter = new SwipeStackAdapter(getActivity().getLayoutInflater(), imageLoader, this);
        binding.swipeStack.setAdapter(swipeStackAdapter);
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
