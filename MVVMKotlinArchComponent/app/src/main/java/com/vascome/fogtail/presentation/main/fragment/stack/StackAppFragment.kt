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
import com.vascome.fogtail.data.thread.ExecutionScheduler
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.detail.RecAreaItemDetailActivity
import com.vascome.fogtail.presentation.main.CollectionViewModel
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.stack.adapter.SwipeStackAdapter
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.stack_view_fragment.*
import javax.inject.Inject

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class StackAppFragment : BaseFragment(), CollectionAreaItemListener {

    private val swipeStackAdapter by lazy {
        SwipeStackAdapter(activity.layoutInflater, imageLoader, this)
    }

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
        return inflater.inflate(R.layout.stack_view_fragment, container, false)
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

        viewModel.items
                .observeOn(scheduler.UI())
                .subscribe({ model ->
                    itemsLoadingUi.visibility = if (model.inProgress) VISIBLE else GONE
                    if(!model.inProgress) {
                        if (model.success) {
                            itemsLoadingErrorUi.visibility = GONE
                            swipeStack.visibility = VISIBLE
                            swipeStackAdapter.setData(model.items)
                        } else {
                            swipeStack.visibility = GONE
                            itemsLoadingErrorUi.visibility = VISIBLE
                            swipeStackAdapter.setData(emptyList())
                        }
                    }
                })
                .addTo(disposables)

        itemsLoadingErrorTryAgainButton
                .clicks()
                .subscribe {
                    itemsLoadingErrorUi.visibility = View.GONE
                    viewModel.refreshCommand.accept(true)
                }
                .addTo(disposables)
    }

    private fun initRecyclerView() {
        swipeStack.setAdapter(swipeStackAdapter)
    }

    override fun onDestroyView() {
        viewModel.destroy()
        super.onDestroyView()
    }

    override fun onItemClick(item: RecAreaItem) {
        RecAreaItemDetailActivity.start(activity, item)
    }
}
