package com.vascome.fogtail.presentation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.presentation.base.controllers.BaseViewController
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem

import javax.inject.Inject


@Suppress("MemberVisibilityCanPrivate")
/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class DetailController :
        BaseViewController<DetailContract.View, DetailContract.Presenter, DetailViewState>(),
        DetailContract.View {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createViewState() = DetailViewState()

    override fun onNewViewStateInstance() {
    }

    private var item: RecAreaItem? = null

    @Inject
    lateinit var imageLoader: AppImageLoader

    @Inject
    lateinit var detailPresenter: DetailPresenter

    override fun createPresenter(): DetailContract.Presenter = detailPresenter
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        item = arguments.getParcelable("item")
        return inflater.inflate(R.layout.detail_item_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {

        list_item_description.text = item?.shortDescription()
        list_item_title.text = item?.name()
        item?.imageUrl()?.let { url -> imageLoader.downloadInto(url, list_item_image_view)  }
    }

    companion object {

        fun newInstance(item: RecAreaItem?): DetailController {

            val fragment = DetailController()
            val bundle = Bundle()
            bundle.putParcelable("item", item)
            fragment.arguments = bundle
            return fragment

        }
    }
    */
}
