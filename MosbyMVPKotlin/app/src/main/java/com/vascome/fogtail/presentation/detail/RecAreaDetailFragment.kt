package com.vascome.fogtail.presentation.detail

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vascome.fogtail.R
import com.vascome.fogtail.data.network.AppImageLoader
import com.vascome.fogtail.databinding.DetailItemFragmentBinding
import com.vascome.fogtail.presentation.base.fragments.BaseFragment
import com.vascome.fogtail.presentation.main.domain.model.RecAreaItem

import javax.inject.Inject


/**
 * Created by vasilypopov on 12/6/17
 * Copyright (c) 2017 MVPJava. All rights reserved.
 */

class RecAreaDetailFragment : BaseFragment(), DetailView {


    private lateinit var binding: DetailItemFragmentBinding
    private var item: RecAreaItem? = null

    @Inject
    lateinit var imageLoader: AppImageLoader

    @Inject
    lateinit var presenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        item = arguments.getParcelable("item")
        binding = DataBindingUtil.inflate(inflater!!, R.layout.detail_item_fragment, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    private fun initRecyclerView() {

        binding.listItemDescription.text = item?.shortDescription()
        binding.listItemTitle.text = item?.name()
        if (item?.imageUrl() != null) {
            imageLoader.downloadInto(item!!.imageUrl()!!, binding.listItemImageView)
        }
        presenter.bindView(this)
    }

    override fun onDestroyView() {
        presenter.unbindView(this)
        presenter.destroy()
        binding.unbind()
        super.onDestroyView()
    }

    companion object {

        fun newInstance(item: RecAreaItem?): RecAreaDetailFragment {

            val fragment = RecAreaDetailFragment()
            val bundle = Bundle()
            bundle.putParcelable("item", item)
            fragment.arguments = bundle
            return fragment

        }
    }
}
