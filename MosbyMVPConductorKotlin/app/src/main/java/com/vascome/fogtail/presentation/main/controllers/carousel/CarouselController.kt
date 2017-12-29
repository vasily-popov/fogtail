package com.vascome.fogtail.presentation.main.controllers.carousel

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.databinding.RecyclerRefreshableViewFragmentBinding
import com.vascome.fogtail.presentation.base.controllers.BaseViewController
import com.vascome.fogtail.presentation.main.CollectionContract
import com.vascome.fogtail.presentation.main.CollectionPresenter
import com.vascome.fogtail.presentation.main.CollectionViewState
import com.vascome.fogtail.presentation.MainActivity
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem
import com.vascome.fogtail.presentation.main.controllers.gallery.adapter.GalleryAreaAdapter
import com.vascome.fogtail.presentation.main.controllers.table.decorator.BoxSpaceItemDecoration
import com.vascome.fogtail.presentation.main.utils.CollectionAreaItemListener

import javax.inject.Inject


@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class CarouselController :
        BaseViewController<CollectionContract.View, CollectionContract.Presenter, CollectionViewState>(),
        CollectionContract.View,
        CollectionAreaItemListener {

    @Inject
    lateinit var collectionPresenter: CollectionPresenter

    @Inject
    lateinit var imageLoader: AppImageLoader

    lateinit private var galleryAreaAdapter : GalleryAreaAdapter
    private lateinit var binding: RecyclerRefreshableViewFragmentBinding


    override fun createViewState()= CollectionViewState()
    override fun onNewViewStateInstance() = presenter.reloadItems()

    override fun createPresenter(): CollectionContract.Presenter = collectionPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        if(!isInjected) {
            (activity as MainActivity).collectionComponent().carouselComponent().inject(this)
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
        binding.itemsLoadingErrorUi.visibility = View.GONE
        binding.recyclerViewSwipeRefresh.visibility = View.VISIBLE
        galleryAreaAdapter.setData(items)
    }

    override fun showError() {
        viewState.setError()
        binding.recyclerViewSwipeRefresh.visibility = View.GONE
        binding.itemsLoadingErrorUi.visibility = View.VISIBLE
    }

    private fun initRecyclerView(context:Context) {

        galleryAreaAdapter = GalleryAreaAdapter(activity!!.layoutInflater, imageLoader, this)
        binding.recyclerView.addItemDecoration(BoxSpaceItemDecoration(resources!!.getDimension(R.dimen.list_item_vertical_space_between_items).toInt()))
        val llm = CarouselLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = llm
        binding.recyclerView.adapter = galleryAreaAdapter
        binding.recyclerViewSwipeRefresh.setOnRefreshListener { presenter.reloadItems() }
        binding.itemsLoadingErrorTryAgainButton.setOnClickListener {
            binding.itemsLoadingErrorUi.visibility = View.GONE
            binding.recyclerViewSwipeRefresh.visibility = View.VISIBLE
            galleryAreaAdapter.setData(emptyList())
            presenter.reloadItems()
        }
    }

    override fun onItemClick(clickedItem: RecAreaItem) {
        //presenter.openItemDetail(clickedItem)
    }
}
