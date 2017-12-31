package com.vascome.fogtail.presentation.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var scheduler: ExecutionScheduler

    lateinit var viewModel: DetailViewModel
    var item: RecAreaItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        item = arguments.getParcelable("item")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_item_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)
        viewModel.imageUrl.observe(this, Observer<String> { url ->
            url?.let {
                imageLoader.downloadInto(url, listItemImageView)
            }
        })
        viewModel.name.observe(this, Observer<String>  { name ->
            listItemTitle.text = name
        })

        viewModel.itemDescription.observe(this, Observer<String>  { name ->
            listItemDescription.text = name
        })

        viewModel.setItem(item)
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
