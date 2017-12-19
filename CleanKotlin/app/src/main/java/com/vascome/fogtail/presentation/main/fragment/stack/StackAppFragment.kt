package com.vascome.fogtail.presentation.main.fragment.stack

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.databinding.StackViewFragmentBinding
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.CollectionContract
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.stack.adapter.SwipeStackAdapter
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener

import javax.inject.Inject

import android.view.View.GONE
import android.view.View.VISIBLE

/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class StackAppFragment : BaseFragment(), CollectionContract.View, CollectionAreaItemListener {

    private var swipeStackAdapter: SwipeStackAdapter? = null
    internal lateinit var binding: StackViewFragmentBinding

    @Inject
    lateinit var presenter: CollectionPresenter

    @Inject
    lateinit var imageLoader: AppImageLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater!!, R.layout.stack_view_fragment, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
        presenter.reloadItems()
    }

    override fun setLoadingIndicator(active: Boolean) {

        runIfFragmentAlive(Runnable {
            binding.itemsLoadingUi.visibility = if (active) VISIBLE else GONE
        })
    }

    override fun showItems(items: List<RecAreaItem>) {
        runIfFragmentAlive(Runnable {
            binding.itemsLoadingErrorUi.visibility = GONE
            binding.swipeStack.visibility = VISIBLE
            swipeStackAdapter?.setData(items)
        })
    }

    override fun showError() {

        runIfFragmentAlive(Runnable {
            binding.itemsLoadingErrorUi.visibility = VISIBLE
            binding.swipeStack.visibility = GONE
        })

    }

    private fun initRecyclerView() {

        swipeStackAdapter = SwipeStackAdapter(activity.layoutInflater, imageLoader, this)
        binding.swipeStack.setAdapter(swipeStackAdapter!!)

        presenter.bindView(this)
    }

    override fun onDestroyView() {
        presenter.unbindView(this)
        presenter.destroy()
        binding.unbind()
        super.onDestroyView()
    }

    override fun onItemClick(clickedItem: RecAreaItem) {
        presenter.openItemDetail(clickedItem)
    }
}
