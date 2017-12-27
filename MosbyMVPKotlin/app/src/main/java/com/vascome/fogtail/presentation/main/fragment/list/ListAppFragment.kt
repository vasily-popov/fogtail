package com.vascome.fogtail.presentation.main.fragment.list

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.CollectionContract
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.CollectionViewState
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.list.adapter.ListAreaAdapter
import com.vascome.fogtail.presentation.main.fragment.list.adapter.VerticalSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener
import kotlinx.android.synthetic.main.recycler_refreshable_view_fragment.*

import javax.inject.Inject

@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 12/5/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class ListAppFragment :
        BaseFragment<CollectionContract.View, CollectionContract.Presenter, CollectionViewState>(),
        CollectionContract.View,
        CollectionAreaItemListener {
    @Inject
    lateinit var collectionPresenter: CollectionPresenter

    @Inject
    lateinit var imageLoader: AppImageLoader

    private val listAreaAdapter by lazy {
        ListAreaAdapter(activity.layoutInflater, imageLoader, this)
    }

    override fun createViewState() = CollectionViewState()

    override fun onNewViewStateInstance() {
        presenter.reloadItems()
    }

    override fun createPresenter(): CollectionContract.Presenter = collectionPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recycler_refreshable_view_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        if(!presenter.isInProgress && viewState.isInProgress()) {
            presenter.reloadItems()
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        if(active) {
            viewState.setShowLoading()
        }
        recyclerView_swipe_refresh.post { recyclerView_swipe_refresh.isRefreshing = active  }
    }

    override fun showItems(items: List<RecAreaItem>) {
        viewState.setData(items)
        items_loading_error_ui.visibility = GONE
        recyclerView_swipe_refresh.visibility = VISIBLE
        listAreaAdapter.setData(items)
        if(!isRestoringViewState) {
            runLayoutAnimation()
        }
    }

    override fun showError() {
        viewState.setError()
        recyclerView_swipe_refresh.visibility = GONE
        items_loading_error_ui.visibility = VISIBLE
    }

    private fun initRecyclerView() {

        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = llm
        recyclerView.adapter = listAreaAdapter
        recyclerView_swipe_refresh.setOnRefreshListener { presenter.reloadItems() }
        items_loading_error_try_again_button.setOnClickListener {
            items_loading_error_ui.visibility = GONE
            recyclerView_swipe_refresh.visibility = VISIBLE
            listAreaAdapter.setData(emptyList())
            presenter.reloadItems()
        }
    }

    override fun onItemClick(clickedItem: RecAreaItem) {
        presenter.openItemDetail(clickedItem)
    }

    private fun runLayoutAnimation() {
        AnimationUtils.loadLayoutAnimation(recyclerView.context, R.anim.layout_animation_fall_down).apply {
            recyclerView.layoutAnimation = this
        }
        recyclerView.scheduleLayoutAnimation()
    }
}
