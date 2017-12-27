package com.vascome.fogtail.presentation.main.fragment.gallery

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.view.clicks

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.detail.RecAreaItemDetailActivity
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.CollectionView
import com.vascome.fogtail.presentation.main.CollectionViewState
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.gallery.adapter.GalleryAreaAdapter
import com.vascome.fogtail.presentation.main.fragment.table.decorator.BoxSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener
import io.reactivex.Observable
import kotlinx.android.synthetic.main.recycler_refreshable_view_fragment.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

import javax.inject.Inject

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class GalleryAppFragment
        : BaseFragment<CollectionView, CollectionPresenter>(),
        CollectionView,
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

    override fun createPresenter(): CollectionPresenter = collectionPresenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recycler_refreshable_view_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }


    private fun initRecyclerView() {

        recyclerView.addItemDecoration(BoxSpaceItemDecoration(resources.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val llm = LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = llm
        recyclerView.adapter = galleryAreaAdapter
    }

    override fun loadOnStartIntent(): Observable<Boolean> {
        return Observable.just(true).doOnComplete { Timber.d("start loading completed") }
    }

    override fun pullToRefreshIntent(): Observable<Boolean> {
        return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout).map({ _ -> true })
    }

    override fun retryButtonClickIntent(): Observable<Boolean> {
        return tryAgainButton.clicks()
                .debounce(500, TimeUnit.MICROSECONDS)
                .map { _ -> true }
    }

    override fun render(viewState: CollectionViewState) {
        Timber.d("render %s", viewState)

        when {
            viewState.loading -> renderLoading()
            viewState.data != null -> renderData(viewState)
            viewState.error != null -> renderError()
        }
    }

    private fun renderError() {
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
        swipeRefreshLayout.visibility = View.GONE
        errorContainer.visibility = View.VISIBLE
        galleryAreaAdapter.setData(emptyList())
    }

    private fun renderData(viewState: CollectionViewState) {
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
        errorContainer.visibility = View.GONE
        swipeRefreshLayout.visibility = View.VISIBLE
        galleryAreaAdapter.setData(viewState.data!!)
    }

    private fun renderLoading() {
        errorContainer.visibility = View.GONE
        swipeRefreshLayout.visibility = View.VISIBLE
        swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = true }
    }

    override fun onItemClick(clickedItem: RecAreaItem) {
        RecAreaItemDetailActivity.start(activity, clickedItem)
    }

}