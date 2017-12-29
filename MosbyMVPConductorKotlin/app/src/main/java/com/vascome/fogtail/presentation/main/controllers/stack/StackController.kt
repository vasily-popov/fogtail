package com.vascome.fogtail.presentation.main.controllers.stack

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.presentation.base.controllers.BaseViewController
import com.vascome.fogtail.presentation.main.CollectionContract
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.controllers.stack.adapter.SwipeStackAdapter
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener

import javax.inject.Inject

import android.view.View.GONE
import android.view.View.VISIBLE
import com.vascome.fogtail.databinding.StackViewFragmentBinding
import com.vascome.fogtail.presentation.main.CollectionViewState
import com.vascome.fogtail.presentation.MainActivity

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class StackController :
        BaseViewController<CollectionContract.View, CollectionContract.Presenter, CollectionViewState>(),
        CollectionContract.View,
        CollectionAreaItemListener {

    @Inject
    lateinit var collectionPresenter: CollectionPresenter

    @Inject
    lateinit var imageLoader: AppImageLoader

    private lateinit var swipeStackAdapter : SwipeStackAdapter
    private lateinit var binding: StackViewFragmentBinding

    override fun createViewState() = CollectionViewState()
    override fun onNewViewStateInstance() = presenter.reloadItems()
    override fun createPresenter(): CollectionContract.Presenter = collectionPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        if(!isInjected) {
            (activity as MainActivity).collectionComponent().stackComponent().inject(this)
            isInjected = true
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.stack_view_fragment, container, false)
        initRecyclerView()
        return binding.root
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        if(!presenter.isInProgress && viewState.isInProgress()) {
            presenter.reloadItems()
        }
    }

    override fun setLoadingIndicator(active: Boolean) {

        if(active) {
            viewState.setShowLoading()
        }
        binding.itemsLoadingUi.visibility = if (active) VISIBLE else GONE
    }

    override fun showItems(items: List<RecAreaItem>) {
        viewState.setData(items)
        binding.itemsLoadingErrorUi.visibility = GONE
        binding.swipeStack.visibility = VISIBLE
        swipeStackAdapter.setData(items)
    }

    override fun showError() {

        viewState.setError()
        binding.itemsLoadingErrorUi.visibility = VISIBLE
        binding.swipeStack.visibility = GONE
    }

    private fun initRecyclerView() {
        swipeStackAdapter = SwipeStackAdapter(activity!!.layoutInflater, imageLoader, this)
        binding.swipeStack.setAdapter(swipeStackAdapter)
        binding.itemsLoadingErrorTryAgainButton.setOnClickListener{ presenter.reloadItems() }
    }

    override fun onItemClick(clickedItem: RecAreaItem) {
        //presenter.openItemDetail(clickedItem)
    }
}
