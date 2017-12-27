package com.vascome.fogtail.presentation.main.fragment.stack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.CollectionContract
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.stack.adapter.SwipeStackAdapter
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener

import javax.inject.Inject

import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.stack_view_fragment.*

@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class StackAppFragment :
        BaseFragment<CollectionContract.View, CollectionContract.Presenter>(),
        CollectionContract.View,
        CollectionAreaItemListener {

    @Inject
    lateinit var collectionPresenter: CollectionPresenter

    @Inject
    lateinit var imageLoader: AppImageLoader

    private val swipeStackAdapter by lazy {
        SwipeStackAdapter(activity.layoutInflater, imageLoader, this)
    }


    override fun createPresenter(): CollectionContract.Presenter = collectionPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.stack_view_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        presenter.reloadItems()
    }

    override fun setLoadingIndicator(active: Boolean) {

        items_loading_ui.visibility = if (active) VISIBLE else GONE
    }

    override fun showItems(items: List<RecAreaItem>) {
        items_loading_error_ui.visibility = GONE
        swipeStack.visibility = VISIBLE
        swipeStackAdapter.setData(items)
    }

    override fun showError() {

        items_loading_error_ui.visibility = VISIBLE
        swipeStack.visibility = GONE
    }

    private fun initRecyclerView() {
        swipeStack.setAdapter(swipeStackAdapter)
    }

    override fun onDestroyView() {
        presenter.dispose()
        super.onDestroyView()
    }

    override fun onItemClick(clickedItem: RecAreaItem) {
        presenter.openItemDetail(clickedItem)
    }
}
