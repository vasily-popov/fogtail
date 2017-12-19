package com.vascome.fogtail.presentation.main.fragment.list

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.databinding.RecyclerRefreshableViewFragmentBinding
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.CollectionContract
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.fragment.list.adapter.ListAreaAdapter
import com.vascome.fogtail.presentation.main.fragment.list.adapter.VerticalSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener

import javax.inject.Inject

/**
 * Created by vasilypopov on 12/5/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class ListAppFragment : BaseFragment(), CollectionContract.View, CollectionAreaItemListener {

    private var listAreaAdapter: ListAreaAdapter? = null
    private lateinit var binding: RecyclerRefreshableViewFragmentBinding

    @Inject
    lateinit var presenter: CollectionPresenter

    @Inject
    lateinit var imageLoader: AppImageLoader


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater!!, R.layout.recycler_refreshable_view_fragment, container, false)
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
            binding.recyclerViewSwipeRefresh.post { binding.recyclerViewSwipeRefresh.isRefreshing = active }
        })
    }

    override fun showItems(items: List<RecAreaItem>) {
        runIfFragmentAlive(Runnable {
            binding.itemsLoadingErrorUi.visibility = View.GONE
            binding.recyclerViewSwipeRefresh.visibility = View.VISIBLE
            listAreaAdapter?.setData(items)
        })
    }

    override fun showError() {

        runIfFragmentAlive(Runnable {
            binding.recyclerViewSwipeRefresh.visibility = View.GONE
            binding.itemsLoadingErrorUi.visibility = View.VISIBLE
        })

    }

    private fun initRecyclerView() {

        binding.recyclerView.addItemDecoration(VerticalSpaceItemDecoration(resources.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = llm
        listAreaAdapter = ListAreaAdapter(activity.layoutInflater, imageLoader, this)
        binding.recyclerView.adapter = listAreaAdapter
        binding.recyclerViewSwipeRefresh.setOnRefreshListener { presenter.reloadItems() }

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
