package com.vascome.fogtail.presentation.main.fragment.stack

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jakewharton.rxbinding2.view.RxView
import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.data.thread.ExecutionScheduler
import com.vascome.fogtail.databinding.StackViewFragmentBinding
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.CollectionViewModel
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.stack.adapter.SwipeStackAdapter
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener

import javax.inject.Inject

import android.view.View.GONE
import android.view.View.VISIBLE
import io.reactivex.rxkotlin.addTo

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class StackAppFragment : BaseFragment(), CollectionAreaItemListener {

    private lateinit var swipeStackAdapter: SwipeStackAdapter
    internal lateinit var binding: StackViewFragmentBinding

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
        binding = DataBindingUtil.inflate(inflater, R.layout.stack_view_fragment, container, false)
        return binding.root
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
/*
        viewModel.inProgress
                .observeOn(scheduler.UI())
                .subscribe { value -> binding.itemsLoadingUi.visibility = if (value) VISIBLE else GONE }
                .addTo(disposables)

        viewModel.items
                .observeOn(scheduler.UI())
                .subscribe({ recAreaItems ->

                    if (recAreaItems.isNotEmpty()) {
                        binding.itemsLoadingErrorUi.visibility = View.GONE
                        binding.swipeStack.visibility = View.VISIBLE
                        swipeStackAdapter.setData(recAreaItems)
                    } else {
                        binding.swipeStack.visibility = View.GONE
                        binding.itemsLoadingErrorUi.visibility = View.VISIBLE
                    }
                })
                .addTo(disposables)

        RxView.clicks(binding.itemsLoadingErrorTryAgainButton)
                .subscribe { _ -> viewModel.refreshCommand.accept(true) }
                .addTo(disposables)
                */
    }

    private fun initRecyclerView() {
        swipeStackAdapter = SwipeStackAdapter(activity.layoutInflater, imageLoader, this)
        binding.swipeStack.setAdapter(swipeStackAdapter)
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
