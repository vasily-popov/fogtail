package com.vascome.fogtail.ui.stack;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vascome.fogtail.FogtailApplication;
import com.vascome.fogtail.R;
import com.vascome.fogtail.api.entities.RecAreaItem;
import com.vascome.fogtail.databinding.StackViewFragmentBinding;
import com.vascome.fogtail.models.AnalyticsModel;
import com.vascome.fogtail.models.AppImageLoader;
import com.vascome.fogtail.models.RecAreaItemsModel;
import com.vascome.fogtail.ui.base.fragments.BaseFragment;
import com.vascome.fogtail.ui.collectionbase.CollectionPresenter;
import com.vascome.fogtail.ui.collectionbase.ICollectionView;
import com.vascome.fogtail.ui.stack.adapter.SwipeStackAdapter;

import java.util.List;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

public class StackAppFragment extends BaseFragment implements ICollectionView {

    Context appContext;
    SwipeStackAdapter swipeStackAdapter;

    @Inject
    CollectionPresenter presenter;

    @Inject
    AppImageLoader networkBitmapClient;

    StackViewFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appContext = getActivity().getApplicationContext();
        FogtailApplication.get(appContext).applicationComponent().plus(new StackFragmentModule()).inject(this);
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
        presenter.reloadData();
    }

    @Override
    public void setLoadingIndicator(boolean active) {

        runOnUiThreadIfFragmentAlive(() -> binding.itemsLoadingUi.setVisibility(active?VISIBLE:GONE));
    }

    @Override
    public void showItems(List<RecAreaItem> items) {
        runOnUiThreadIfFragmentAlive(() -> {
            setLoadingIndicator(false);
            binding.itemsLoadingErrorUi.setVisibility(GONE);
            binding.swipeStack.setVisibility(VISIBLE);

            if (swipeStackAdapter != null) {
                swipeStackAdapter.setData(items);
            }
        });
    }

    @Override
    public void showError() {

        runOnUiThreadIfFragmentAlive(() -> {
            setLoadingIndicator(false);
            binding.itemsLoadingErrorUi.setVisibility(VISIBLE);
            binding.swipeStack.setVisibility(GONE);
        });

    }

    private void initRecyclerView() {

        swipeStackAdapter = new SwipeStackAdapter(getActivity().getLayoutInflater(), networkBitmapClient);
        binding.swipeStack.setAdapter(swipeStackAdapter);

        presenter.bindView(this);
    }

    @Override
    public void onDestroyView() {
        presenter.unbindView(this);
        binding.unbind();
        super.onDestroyView();
    }

    @Subcomponent(modules = StackFragmentModule.class)
    public interface StackFragmentComponent {
        void inject(@NonNull StackAppFragment itemsFragment);
    }

    @Module
    public class StackFragmentModule {

        @Provides
        @NonNull
        public CollectionPresenter provideItemsPresenter(@NonNull RecAreaItemsModel itemsModel,
                                                   @NonNull AnalyticsModel analyticsModel) {
            return new CollectionPresenter(itemsModel, analyticsModel);
        }
    }

}
