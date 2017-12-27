package com.vascome.fogtail.presentation.main.fragment.stack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.clicks
import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.detail.RecAreaItemDetailActivity
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.CollectionView
import com.vascome.fogtail.presentation.main.CollectionViewState
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.stack.adapter.SwipeStackAdapter
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener
import io.reactivex.Observable
import kotlinx.android.synthetic.main.stack_view_fragment.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class StackAppFragment
    : BaseFragment<CollectionView, CollectionPresenter>(),
        CollectionView,
        CollectionAreaItemListener {

    @Inject
    lateinit var collectionPresenter: CollectionPresenter

    @Inject
    lateinit var imageLoader: AppImageLoader

    private val swipeStackAdapter by lazy {
        SwipeStackAdapter(activity.layoutInflater, imageLoader, this)
    }

    override fun createPresenter(): CollectionPresenter = collectionPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.stack_view_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        swipeStack.setAdapter(swipeStackAdapter)
    }

    override fun loadOnStartIntent(): Observable<Boolean> {
        return Observable.just(true).doOnComplete { Timber.d("start loading completed") }
    }

    override fun pullToRefreshIntent(): Observable<Boolean> {
        return Observable.never()
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
        progressView.visibility = GONE
        swipeStack.visibility = GONE
        errorContainer.visibility = VISIBLE
        swipeStackAdapter.setData(emptyList())
    }

    private fun renderData(viewState: CollectionViewState) {
        progressView.visibility = GONE
        errorContainer.visibility = GONE
        swipeStack.visibility = VISIBLE
        swipeStackAdapter.setData(viewState.data!!)
    }

    private fun renderLoading() {
        progressView.visibility = VISIBLE
        errorContainer.visibility = GONE
    }

    override fun onItemClick(clickedItem: RecAreaItem) {
        RecAreaItemDetailActivity.start(activity, clickedItem)
    }
}
