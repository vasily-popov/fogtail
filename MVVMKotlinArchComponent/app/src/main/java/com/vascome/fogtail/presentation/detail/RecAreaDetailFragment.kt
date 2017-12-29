package com.vascome.fogtail.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jakewharton.rxbinding2.widget.RxTextView
import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.data.thread.ExecutionScheduler
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.dto.RecAreaItem
import kotlinx.android.synthetic.main.detail_item_fragment.*

import javax.inject.Inject


/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class RecAreaDetailFragment : BaseFragment() {

    @Inject
    lateinit var imageLoader: AppImageLoader

    @Inject
    lateinit var viewModel: DetailViewModel

    @Inject
    lateinit var scheduler: ExecutionScheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val item = arguments.getParcelable<RecAreaItem>("item")
        if (item != null) {
            viewModel.setItemCommand.accept(item)
        }
        return inflater.inflate(R.layout.detail_item_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        subscribeEvents()
    }

    private fun subscribeEvents() {

        disposables.add(
                viewModel.itemDescription()
                        .observeOn(scheduler.UI())
                        .subscribe(RxTextView.text(listItemDescription))
        )

        disposables.add(
                viewModel.name()
                        .observeOn(scheduler.UI())
                        .subscribe(RxTextView.text(listItemTitle))
        )

        disposables.add(
                viewModel.imageUrl()
                        .observeOn(scheduler.UI())
                        .subscribe { url ->
                            url?.let { url1 -> imageLoader.downloadInto(url1, listItemImageView) }
                        }
        )
    }

    override fun onDestroyView() {
        viewModel.destroy()
        super.onDestroyView()
    }

    companion object {

        fun newInstance(item: RecAreaItem): RecAreaDetailFragment {

            val fragment = RecAreaDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("item", item)
            fragment.arguments = bundle
            return fragment

        }
    }
}
