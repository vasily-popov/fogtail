package com.vascome.fogtail.presentation.main.fragment.gallery

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.CollectionContract
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.CollectionViewState
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.gallery.adapter.GalleryAreaAdapter
import com.vascome.fogtail.presentation.main.fragment.table.decorator.BoxSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener
import kotlinx.android.synthetic.main.recycler_refreshable_view_fragment.*

import javax.inject.Inject

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class GalleryAppFragment
        : BaseFragment<CollectionContract.View, CollectionContract.Presenter, CollectionViewState>(),
            CollectionContract.View,
            CollectionAreaItemListener {

    @Inject
    lateinit var appContext: Context

    @Inject
    lateinit var collectionPresenter: CollectionPresenter

    @Inject
    lateinit var imageLoader: AppImageLoader

    private val galleryAreaAdapter by lazy {
        GalleryAreaAdapter(activity.layoutInflater, imageLoader, this)
    }


    override fun onNewViewStateInstance() {
    }
    override fun createViewState()= CollectionViewState()
    override fun createPresenter(): CollectionContract.Presenter = collectionPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recycler_refreshable_view_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        if(savedInstanceState == null) {
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
        items_loading_error_ui.visibility = View.GONE
        recyclerView_swipe_refresh.visibility = View.VISIBLE
        galleryAreaAdapter.setData(items)
    }

    override fun showError() {

        viewState.setError()
        recyclerView_swipe_refresh.visibility = View.GONE
        items_loading_error_ui.visibility = View.VISIBLE

    }

    private fun initRecyclerView() {

        recyclerView.addItemDecoration(BoxSpaceItemDecoration(resources.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val llm = LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = llm
        recyclerView.adapter = galleryAreaAdapter
        recyclerView_swipe_refresh.setOnRefreshListener { presenter.reloadItems() }
        items_loading_error_try_again_button.setOnClickListener{ presenter.reloadItems() }
    }

    override fun onItemClick(clickedItem: RecAreaItem) {
        presenter.openItemDetail(clickedItem)
    }

}