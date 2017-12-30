package com.vascome.fogtail.presentation.main.fragment.table

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.view.clicks
import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.data.thread.ExecutionScheduler
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.detail.RecAreaItemDetailActivity
import com.vascome.fogtail.presentation.main.CollectionStateModel
import com.vascome.fogtail.presentation.main.CollectionViewModel
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.list.adapter.ListAreaAdapter
import com.vascome.fogtail.presentation.main.fragment.table.decorator.BoxSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.recycler_refreshable_view_fragment.*
import javax.inject.Inject


/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class GridAppFragment : BaseFragment(), CollectionAreaItemListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var imageLoader: AppImageLoader

    @Inject
    lateinit var scheduler: ExecutionScheduler

    private lateinit var listAreaAdapter: ListAreaAdapter
    private lateinit var viewModel: CollectionViewModel

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

    private fun initView() {

        recyclerView.addItemDecoration(BoxSpaceItemDecoration(resources.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val glm = GridLayoutManager(context, 2)
        recyclerView.layoutManager = glm
        listAreaAdapter = ListAreaAdapter(activity.layoutInflater, imageLoader, this)
        recyclerView.adapter = listAreaAdapter

        RxSwipeRefreshLayout.refreshes(recyclerViewSwipeRefresh)
                .map { _ -> true }
                .subscribe { viewModel.loadItems() }
                .addTo(disposables)

        itemsLoadingErrorTryAgainButton
                .clicks()
                .subscribe {
                    itemsLoadingErrorUi.visibility = View.GONE
                    recyclerViewSwipeRefresh.visibility = View.VISIBLE
                    viewModel.loadItems()
                }
                .addTo(disposables)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CollectionViewModel::class.java)
        viewModel.items.observe(this, Observer<CollectionStateModel> { model ->
            model?.let {
                recyclerViewSwipeRefresh.post { recyclerViewSwipeRefresh.isRefreshing = model.inProgress  }
                if(!model.inProgress) {
                    if (model.success) {
                        itemsLoadingErrorUi.visibility = View.GONE
                        recyclerViewSwipeRefresh.visibility = View.VISIBLE
                        listAreaAdapter.setData(model.items)
                    } else {
                        recyclerViewSwipeRefresh.visibility = View.GONE
                        itemsLoadingErrorUi.visibility = View.VISIBLE
                        listAreaAdapter.setData(emptyList())
                    }
                }
            }
        })
    }

    override fun onItemClick(item: RecAreaItem) {
        RecAreaItemDetailActivity.start(activity, item)
    }
}