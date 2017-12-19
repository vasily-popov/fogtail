package com.vascome.fogtail.presentation.main.fragment.list

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jakewharton.rxbinding2.support.v4.widget.RxSwipeRefreshLayout
import com.jakewharton.rxbinding2.view.RxView
import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.data.thread.ExecutionScheduler
import com.vascome.fogtail.databinding.RecyclerRefreshableViewFragmentBinding
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.CollectionViewModel
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.list.adapter.ListAreaAdapter
import com.vascome.fogtail.presentation.main.fragment.list.adapter.VerticalSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener

import javax.inject.Inject

/**
 * Created by vasilypopov on 12/5/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class ListAppFragment : BaseFragment(), CollectionAreaItemListener {

    private lateinit var listAreaAdapter: ListAreaAdapter
    internal lateinit var binding: RecyclerRefreshableViewFragmentBinding

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.recycler_refreshable_view_fragment, container, false)
        return binding.root
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
        disposables.add(
                viewModel.showProgress()
                        .observeOn(scheduler.UI())
                        .subscribe(RxSwipeRefreshLayout.refreshing(binding.recyclerViewSwipeRefresh)))

        disposables.add(
                RxSwipeRefreshLayout.refreshes(binding.recyclerViewSwipeRefresh)
                        .map { _ -> true }
                        .subscribe { value -> viewModel.refreshCommand.accept(value) })

        disposables.add(
                viewModel.items()
                        .observeOn(scheduler.UI())
                        .subscribe({ recAreaItems ->
                            if (recAreaItems.isNotEmpty()) {
                                binding.itemsLoadingErrorUi.visibility = View.GONE
                                binding.recyclerViewSwipeRefresh.visibility = View.VISIBLE
                                listAreaAdapter.setData(recAreaItems)
                            } else {
                                binding.recyclerViewSwipeRefresh.visibility = View.GONE
                                binding.itemsLoadingErrorUi.visibility = View.VISIBLE
                            }
                        })
        )

        disposables.add(
                RxView.clicks(binding.itemsLoadingErrorTryAgainButton)
                        .subscribe { _ -> viewModel.refreshCommand.accept(true) })
    }

    private fun initView() {

        binding.recyclerView.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val llm = LinearLayoutManager(appContext, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = llm
        listAreaAdapter = ListAreaAdapter(activity.layoutInflater, imageLoader, this)
        binding.recyclerView.adapter = listAreaAdapter
    }

    override fun onDestroyView() {
        viewModel.destroy()
        binding.unbind()
        super.onDestroyView()
    }

    override fun onItemClick(item: RecAreaItem) {
        viewModel.openDetailCommand.accept(item)
    }
}
