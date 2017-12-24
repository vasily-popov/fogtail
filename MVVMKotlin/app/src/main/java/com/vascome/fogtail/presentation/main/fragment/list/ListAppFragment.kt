package com.vascome.fogtail.presentation.main.fragment.list

import android.annotation.SuppressLint
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
import com.vascome.fogtail.data.thread.ExecutionScheduler
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.CollectionViewModel
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.list.adapter.ListAreaAdapter
import com.vascome.fogtail.presentation.main.fragment.list.adapter.VerticalSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.recycler_refreshable_view_fragment.*

import javax.inject.Inject

/**
 * Created by vasilypopov on 12/5/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class ListAppFragment : BaseFragment(), CollectionAreaItemListener {

    private lateinit var listAreaAdapter: ListAreaAdapter

    @Inject
    lateinit var appContext: Context

    @Inject
    lateinit var viewModel: CollectionViewModel

    @Inject
    lateinit var imageLoader: AppImageLoader

    @Inject
    lateinit var scheduler: ExecutionScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recycler_refreshable_view_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    override fun onResume() {
        super.onResume()
        subscribeEvents()
        viewModel.refreshCommand.accept(true)
    }

    @SuppressLint("RxSubscribeOnError")
    private fun subscribeEvents() {
        viewModel.inProgress
                .observeOn(scheduler.UI())
                .subscribe(RxSwipeRefreshLayout.refreshing(recyclerView_swipe_refresh))
                .addTo(disposables)

        RxSwipeRefreshLayout.refreshes(recyclerView_swipe_refresh)
                .map { _ -> true }
                .subscribe { value -> viewModel.refreshCommand.accept(value) }
                .addTo(disposables)

        viewModel.items
                .observeOn(scheduler.UI())
                .subscribe({ recAreaItems ->
                    if (recAreaItems.isNotEmpty()) {
                        items_loading_error_ui.visibility = View.GONE
                        recyclerView_swipe_refresh.visibility = View.VISIBLE
                        listAreaAdapter.setData(recAreaItems)
                    } else {
                        recyclerView_swipe_refresh.visibility = View.GONE
                        items_loading_error_ui.visibility = View.VISIBLE
                    }
                })
                .addTo(disposables)
        items_loading_error_try_again_button.clicks()
                .subscribe {
                    items_loading_error_ui.visibility = View.GONE
                    recyclerView_swipe_refresh.visibility = View.VISIBLE
                    viewModel.refreshCommand.accept(true)   }
                .addTo(disposables)
    }

    private fun initView() {

        recyclerView.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val llm = LinearLayoutManager(appContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = llm
        listAreaAdapter = ListAreaAdapter(activity.layoutInflater, imageLoader, this)
        recyclerView.adapter = listAreaAdapter
    }

    override fun onDestroyView() {
        viewModel.destroy()
        super.onDestroyView()
    }

    override fun onItemClick(item: RecAreaItem) {
        viewModel.openDetailCommand.accept(item)
    }
}
