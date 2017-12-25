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
import com.vascome.fogtail.data.thread.ExecutionScheduler
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.CollectionViewModel
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.gallery.adapter.GalleryAreaAdapter
import com.vascome.fogtail.presentation.main.fragment.table.decorator.BoxSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.recycler_refreshable_view_fragment.*

import javax.inject.Inject

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class GalleryAppFragment : BaseFragment(), CollectionAreaItemListener {

    lateinit private var galleryAreaAdapter: GalleryAreaAdapter

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
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        subscribeEvents()
        viewModel.refreshCommand.accept(true)
    }

    private fun subscribeEvents() {
        RxSwipeRefreshLayout.refreshes(recyclerViewSwipeRefresh)
                .map { _ -> true }
                .subscribe {
                    value ->
                    viewModel.refreshCommand.accept(value)
                }
                .addTo(disposables)

        viewModel.items
                .observeOn(scheduler.UI())
                .subscribe({ model ->
                    recyclerViewSwipeRefresh.post { recyclerViewSwipeRefresh.isRefreshing = model.inProgress  }
                    if(!model.inProgress) {
                        if (model.success) {
                            itemsLoadingErrorUi.visibility = View.GONE
                            recyclerViewSwipeRefresh.visibility = View.VISIBLE
                            galleryAreaAdapter.setData(model.items)
                        } else {
                            recyclerViewSwipeRefresh.visibility = View.GONE
                            itemsLoadingErrorUi.visibility = View.VISIBLE
                            galleryAreaAdapter.setData(emptyList())
                        }
                    }
                })
                .addTo(disposables)

        itemsLoadingErrorTryAgainButton
                .clicks()
                .subscribe {
                    itemsLoadingErrorUi.visibility = View.GONE
                    recyclerViewSwipeRefresh.visibility = View.VISIBLE
                    viewModel.refreshCommand.accept(true)
                }
                .addTo(disposables)
    }


    private fun initRecyclerView() {

        recyclerView.addItemDecoration(BoxSpaceItemDecoration(resources.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val llm = LinearLayoutManager(appContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = llm
        galleryAreaAdapter = GalleryAreaAdapter(activity.layoutInflater, imageLoader, this)
        recyclerView.adapter = galleryAreaAdapter
    }

    override fun onDestroyView() {
        viewModel.destroy()
        super.onDestroyView()
    }

    override fun onItemClick(item: RecAreaItem) {
        viewModel.openDetailCommand.accept(item)
    }

}