package com.vascome.fogtail.presentation.main.controllers.list

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.vascome.fogtail.R

import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.databinding.RecyclerRefreshableViewFragmentBinding
import com.vascome.fogtail.presentation.base.controllers.BaseViewController
import com.vascome.fogtail.presentation.main.CollectionContract
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.CollectionViewState
import com.vascome.fogtail.presentation.MainActivity
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.controllers.list.adapter.ListAreaAdapter
import com.vascome.fogtail.presentation.main.controllers.list.adapter.VerticalSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener

import javax.inject.Inject
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.vascome.fogtail.presentation.detail.DetailController


@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 12/5/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class ListViewController :
        BaseViewController<CollectionContract.View, CollectionContract.Presenter, CollectionViewState>(),
        CollectionContract.View,
        CollectionAreaItemListener {

    @Inject
    lateinit var collectionPresenter: CollectionPresenter

    @Inject
    lateinit var imageLoader: AppImageLoader

    private lateinit var listAreaAdapter : ListAreaAdapter
    private lateinit var binding: RecyclerRefreshableViewFragmentBinding

    override fun createViewState() = CollectionViewState()

    override fun onNewViewStateInstance() {
        presenter.reloadItems()
    }


    override fun createPresenter(): CollectionContract.Presenter = collectionPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        if(!isInjected) {
            (activity as MainActivity).collectionComponent().listComponent().inject(this)
            isInjected = true
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.recycler_refreshable_view_fragment, container, false)
        initRecyclerView(binding.root.context)
        return binding.root
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        if(!presenter.isInProgress && viewState.isInProgress()) {
            presenter.reloadItems()
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        if(active) {
            viewState.setShowLoading()
        }
        binding.recyclerViewSwipeRefresh.post { binding.recyclerViewSwipeRefresh.isRefreshing = active  }
    }

    override fun showItems(items: List<RecAreaItem>) {
        viewState.setData(items)
        binding.itemsLoadingErrorUi.visibility = GONE
        binding.recyclerViewSwipeRefresh.visibility = VISIBLE
        listAreaAdapter.setData(items)
        if(!isRestoringViewState) {
            runLayoutAnimation()
        }
    }

    override fun showError() {
        viewState.setError()
        binding.recyclerViewSwipeRefresh.visibility = GONE
        binding.itemsLoadingErrorUi.visibility = VISIBLE
    }

    private fun initRecyclerView(context : Context) {

        listAreaAdapter = ListAreaAdapter(activity!!.layoutInflater, imageLoader, this)
        binding.recyclerView.addItemDecoration(VerticalSpaceItemDecoration(resources!!.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = llm
        binding.recyclerView.adapter = listAreaAdapter
        binding.recyclerViewSwipeRefresh.setOnRefreshListener { presenter.reloadItems() }
        binding.itemsLoadingErrorTryAgainButton.setOnClickListener {
            binding.itemsLoadingErrorUi.visibility = GONE
            binding.recyclerViewSwipeRefresh.visibility = VISIBLE
            listAreaAdapter.setData(emptyList())
            presenter.reloadItems()
        }
    }

    override fun onItemClick(clickedItem: RecAreaItem) {
        router.pushController(getRouterTransaction(clickedItem))
    }

    private fun runLayoutAnimation() {
        AnimationUtils.loadLayoutAnimation(binding.recyclerView.context, R.anim.layout_animation_fall_down).apply {
            binding.recyclerView.layoutAnimation = this
        }
        binding.recyclerView.scheduleLayoutAnimation()
    }

    fun getRouterTransaction(item: RecAreaItem): RouterTransaction {
        val toController = DetailController.newInstance(item)

        return RouterTransaction.with(toController)
                .pushChangeHandler(HorizontalChangeHandler(true))
                .popChangeHandler(HorizontalChangeHandler(true))
    }
}
